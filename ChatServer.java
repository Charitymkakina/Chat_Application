import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatServer {


public static ArrayList<ClientHandler> clients =
        new ArrayList<>();

private static HashMap<String, ClientHandler> onlineUsers =
        new HashMap<>();

private static HashMap<String, String> userRooms =
        new HashMap<>();

public static void main(String[] args) {

    UserManager userManager = new UserManager();

    try {

        ServerSocket serverSocket =
                new ServerSocket(5000);

        System.out.println("Server started...");

        while (true) {

            Socket socket =
                    serverSocket.accept();

            new Thread(
                    () -> handleClient(socket, userManager)
            ).start();
        }

    } catch (IOException e) {

        System.out.println(
                "Server error: "
                        + e.getMessage()
        );
    }
}

private static void handleClient(
        Socket socket,
        UserManager userManager
) {

    try {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

        PrintWriter writer =
                new PrintWriter(
                        socket.getOutputStream(),
                        true
                );

        String loginData =
                reader.readLine();

        if (loginData == null
                || !loginData.contains(",")) {

            writer.println("LOGIN_FAILED");
            socket.close();
            return;
        }

        String[] credentials =
                loginData.split(",", 2);

        String username =
                credentials[0].trim();

        String password =
                credentials[1].trim();

        if (!userManager.loginUser(
                username,
                password
        )) {

            writer.println("LOGIN_FAILED");
            socket.close();
            return;
        }

        writer.println("LOGIN_SUCCESS");

        ClientHandler handler =
                new ClientHandler(
                        socket,
                        username
                );

        synchronized (clients) {

            clients.add(handler);

            onlineUsers.put(
                    username,
                    handler
            );

            userRooms.put(
                    username,
                    "general"
            );
        }

        new Thread(handler).start();

    } catch (IOException e) {

        System.out.println(
                "Client error: "
                        + e.getMessage()
        );
    }
}

// GLOBAL BROADCAST
public static void broadcast(
        String message
) {

    synchronized (clients) {

        for (ClientHandler client :
                clients) {

            client.send(message);
        }
    }
}

// ROOM BROADCAST
public static void broadcastToRoom(
        String sender,
        String message
) {

    String senderRoom =
            userRooms.get(sender);

    synchronized (clients) {

        for (String username :
                onlineUsers.keySet()) {

            String userRoom =
                    userRooms.get(username);

            if (senderRoom != null
                    && senderRoom.equals(userRoom)) {

                onlineUsers.get(username)
                        .send(message);
            }
        }
    }
}

public static ClientHandler getUser(
        String username
) {

    synchronized (clients) {

        return onlineUsers.get(username);
    }
}

public static String getOnlineUsers() {

    StringBuilder users =
            new StringBuilder(
                    "Online Users:\n"
            );

    synchronized (clients) {

        for (String username :
                onlineUsers.keySet()) {

            String room =
                    userRooms.get(username);

            users.append("- ")
                    .append(username)
                    .append(" (")
                    .append(room)
                    .append(")")
                    .append("\n");
        }
    }

    return users.toString();
}

// STEP 29B - ROOM STATISTICS
public static String getRooms() {

    StringBuilder rooms =
            new StringBuilder(
                    "Available Rooms:\n"
            );

    HashMap<String, Integer> roomCounts =
            new HashMap<>();

    synchronized (clients) {

        for (String room :
                userRooms.values()) {

            roomCounts.put(
                    room,
                    roomCounts.getOrDefault(
                            room,
                            0
                    ) + 1
            );
        }
    }

    for (String room :
            roomCounts.keySet()) {

        rooms.append("- ")
                .append(room)
                .append(" (")
                .append(roomCounts.get(room))
                .append(" users)")
                .append("\n");
    }

    return rooms.toString();
}

public static void removeUser(
        String username
) {

    synchronized (clients) {

        ClientHandler handler =
                onlineUsers.get(username);

        if (handler != null) {

            clients.remove(handler);
        }

        onlineUsers.remove(username);
        userRooms.remove(username);
    }
}

public static void setUserRoom(
        String username,
        String room
) {

    userRooms.put(
            username,
            room
    );
}

public static String getUserRoom(
        String username
) {

    return userRooms.get(
            username
    );
}


}
