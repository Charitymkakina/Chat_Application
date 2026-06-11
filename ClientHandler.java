import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private String username;

    public ClientHandler(Socket socket, String username) {

        this.socket = socket;
        this.username = username;

        try {
            this.writer =
                    new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println("Writer error: " + e.getMessage());
        }
    }

    @Override
    public void run() {

        try {

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ChatServer.broadcast("[SERVER] " + username + " joined the chat.");

            String message;

            while ((message = reader.readLine()) != null) {

                // LOGOUT
                if (message.equalsIgnoreCase("/logout")) {

                    ChatServer.removeUser(username);
                    ChatServer.broadcast("[SERVER] " + username + " left the chat.");
                    socket.close();
                    break;
                }

                // HELP
                if (message.equalsIgnoreCase("/help")) {

                    send("""
Available Commands:
-------------------------
/help
/users
/dm username message
/logout
-------------------------
""");
                    continue;
                }

                // USERS
                if (message.equalsIgnoreCase("/users")) {

                    send(ChatServer.getOnlineUsers());
                    continue;
                }

                // 🔥 DM FEATURE
                if (message.startsWith("/dm ")) {

                    String[] parts = message.split(" ", 3);

                    if (parts.length < 3) {
                        send("Usage: /dm username message");
                        continue;
                    }

                    String targetUser = parts[1];
                    String msg = parts[2];

                    ClientHandler target = ChatServer.getUser(targetUser);

                    if (target == null) {
                        send("User not online: " + targetUser);
                        continue;
                    }

                    target.send("(DM from " + username + "): " + msg);
                    send("(DM sent to " + targetUser + ")");

                    continue;
                }

                // NORMAL MESSAGE
                ChatServer.broadcast(username + ": " + message);
            }

            socket.close();

        } catch (IOException e) {

            System.out.println(username + " disconnected.");
        }
    }

    public void send(String message) {
        writer.println(message);
    }
}