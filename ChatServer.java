import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    public static List<ClientHandler> clients =
            new ArrayList<>();

    public static void main(String[] args) {

        ChatManager chatManager =
                new ChatManager();

        try {

            ServerSocket serverSocket =
                    new ServerSocket(5000);

            System.out.println(
                    "Server started... waiting for clients"
            );

            while (true) {

                Socket socket =
                        serverSocket.accept();

                System.out.println(
                        "New client connected!"
                );

                ClientHandler handler =
                        new ClientHandler(
                                socket,
                                chatManager
                        );

                clients.add(handler);

                Thread thread =
                        new Thread(handler);

                thread.start();
            }

        } catch (IOException e) {

            System.out.println(
                    "Server error: " + e.getMessage()
            );
        }
    }
}