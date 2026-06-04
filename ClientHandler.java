import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            System.out.println("Error creating writer: " + e.getMessage());
        }
    }

    @Override
    public void run() {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream())
                    );

            System.out.println(username + " joined the chat.");

            ChatServer.broadcast("SERVER: " + username + " joined the chat");

            String message;

            while ((message = reader.readLine()) != null) {

                if (message.equalsIgnoreCase("/logout")) {

                    ChatServer.broadcast("SERVER: " + username + " left the chat");
                    System.out.println(username + " disconnected.");
                    break;
                }

                String formattedMessage = username + ": " + message;

                System.out.println(formattedMessage);

                ChatServer.broadcast(formattedMessage);
            }

            socket.close();

        } catch (IOException e) {

            System.out.println(username + " disconnected.");
        }
    }

    // ✅ THIS FIXES YOUR RED SQUIGGLY ERROR
    public void send(String message) {
        writer.println(message);
    }
}