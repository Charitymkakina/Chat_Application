import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ChatServer {

    private static HashMap<String, PrintWriter> clients = new HashMap<>();
    private static HashMap<String, String> userRooms = new HashMap<>();

    public static void main(String[] args) {

        UserManager userManager = new UserManager();

        try {

            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started...");

            while (true) {

                Socket socket = serverSocket.accept();

                new Thread(() -> handleClient(socket, userManager)).start();
            }

        } catch (IOException e) {

            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket, UserManager userManager) {

        try {

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter writer =
                    new PrintWriter(socket.getOutputStream(), true);

            // =========================
            // LOGIN
            // =========================
            String loginData = reader.readLine();

            if (loginData == null || !loginData.contains(",")) {
                writer.println("LOGIN_FAILED");
                socket.close();
                return;
            }

            String[] credentials = loginData.split(",", 2);

            String username = credentials[0].trim();
            String password = credentials[1].trim();

            if (!userManager.loginUser(username, password)) {
                writer.println("LOGIN_FAILED");
                socket.close();
                return;
            }

            writer.println("LOGIN_SUCCESS");

            synchronized (clients) {
                clients.put(username, writer);
            }

            userRooms.put(username, "general");

            broadcast("SERVER: " + username + " joined general");

            // =========================
            // CHAT LOOP
            // =========================
            String message;

            while ((message = reader.readLine()) != null) {

                message = message.trim();

                if (message.equalsIgnoreCase("/logout")) {
                    break;
                }

                // JOIN ROOM
                if (message.startsWith("/join ")) {

                    String room = message.split(" ", 2)[1];

                    userRooms.put(username, room);

                    writer.println("You joined room: " + room);

                    continue;
                }

                // PRIVATE MESSAGE
                if (message.startsWith("/dm ")) {

                    handlePrivateMessage(username, message);
                    continue;
                }

                // ROOM MESSAGE
                broadcastToRoom(username, message);
            }

            synchronized (clients) {
                clients.remove(username);
                userRooms.remove(username);
            }

            broadcast("SERVER: " + username + " left");

            socket.close();

        } catch (IOException e) {

            System.out.println("Client error: " + e.getMessage());
        }
    }

    // =========================
    // ROOM BROADCAST
    // =========================
    private static void broadcastToRoom(String sender, String message) {

        String room = userRooms.get(sender);

        synchronized (clients) {

            for (String user : clients.keySet()) {

                if (room.equals(userRooms.get(user))) {

                    clients.get(user).println(sender + ": " + message);
                }
            }
        }
    }

    // =========================
    // PRIVATE MESSAGE
    // =========================
    private static void handlePrivateMessage(String sender, String message) {

        String[] parts = message.split(" ", 3);

        if (parts.length < 3) return;

        String target = parts[1];
        String msg = parts[2];

        synchronized (clients) {

            PrintWriter writer = clients.get(target);

            if (writer != null) {
                writer.println("(DM from " + sender + "): " + msg);
            }
        }
    }

    // =========================
    // SYSTEM BROADCAST
    // =========================
    private static void broadcast(String message) {

        synchronized (clients) {

            for (PrintWriter writer : clients.values()) {
                writer.println(message);
            }
        }
    }
}