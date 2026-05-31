import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ChatManager chatManager;
    private PrintWriter writer;

    public ClientHandler(Socket socket, ChatManager chatManager) {
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
                    "Error creating writer: " + e.getMessage()
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

            String message;

            while ((message = reader.readLine()) != null) {

                chatManager.sendMessage(
                        "Client",
                        message
                );

                System.out.println(
                        "Received: " + message
                );

                broadcast(message);
            }

        } catch (IOException e) {

            System.out.println(
                    "Client disconnected"
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