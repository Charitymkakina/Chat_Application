import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket =
                    new ServerSocket(5000);

            System.out.println(
                    "Server started. Waiting for client..."
            );

            Socket socket =
                    serverSocket.accept();

            System.out.println(
                    "Client connected!"
            );

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            String message =
                    reader.readLine();

            System.out.println(
                    "Received: " + message
            );

            socket.close();
            serverSocket.close();

        } catch (IOException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}