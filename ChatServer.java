import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatServer {

    // ACTIVE CLIENTS
    public static ArrayList<ClientHandler> clients =
            new ArrayList<>();

    // ONLINE USERS
    private static HashMap<String, ClientHandler> onlineUsers =
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
                    "Server error: " + e.getMessage()
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
            }

            new Thread(handler).start();

        } catch (IOException e) {

            System.out.println(
                    "Client error: "
                            + e.getMessage()
            );
        }
    }

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

    public static String getOnlineUsers() {

        StringBuilder users =
                new StringBuilder(
                        "Online Users:\n"
                );

        synchronized (clients) {

            for (String username :
                    onlineUsers.keySet()) {

                users.append("- ")
                        .append(username)
                        .append("\n");
            }
        }

        return users.toString();
    }

    public static void removeUser(
            String username
    ) {

        synchronized (clients) {

            onlineUsers.remove(username);
        }
    }
}