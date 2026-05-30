import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

        Scanner scanner =
                new Scanner(System.in);

        try {

            Socket socket =
                    new Socket(
                            "localhost",
                            5000
                    );

            PrintWriter writer =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true
                    );

            System.out.print(
                    "Enter message: "
            );

            String message =
                    scanner.nextLine();

            writer.println(message);

            System.out.println(
                    "Message sent!"
            );

            socket.close();

        } catch (IOException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }

        scanner.close();
    }
}