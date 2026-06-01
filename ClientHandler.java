import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ChatManager chatManager;
    private PrintWriter writer;
    private String username;

    public ClientHandler(
            Socket socket,
            ChatManager chatManager
    ) {

        this.socket = socket;
        this.chatManager = chatManager;

        try {

            this.writer =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true
                    );

        } catch (IOException e) {

            System.out.println(
                    "Error creating writer: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public void run() {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            // First message received is username
            username = reader.readLine();

            System.out.println(
                    username + " joined the chat."
            );

            broadcast(
                    username + " joined the chat."
            );

            String message;

            while ((message = reader.readLine()) != null) {

                if (message.equalsIgnoreCase("/logout")) {

                    broadcast(
                            username + " has left the chat."
                    );

                    System.out.println(
                            username + " disconnected."
                    );

                    break;
                }

                String formattedMessage =
                        username + ": " + message;

                chatManager.sendMessage(
                        username,
                        message
                );

                System.out.println(
                        formattedMessage
                );

                broadcast(
                        formattedMessage
                );
            }

            ChatServer.clients.remove(this);

            socket.close();

        } catch (IOException e) {

            System.out.println(
                    "Client disconnected."
            );
        }
    }

    private void broadcast(String message) {

        for (ClientHandler client :
                ChatServer.clients) {

            client.writer.println(message);
        }
    }
}