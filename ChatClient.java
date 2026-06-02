import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();

        System.out.println("=== LOGIN ===");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // LOGIN CHECK FIRST
        if (!userManager.loginUser(username, password)) {
            System.out.println("Access denied. Exiting...");
            return;
        }

        try {

            Socket socket = new Socket("localhost", 5000);

            PrintWriter writer =
                    new PrintWriter(socket.getOutputStream(), true);

            // Send username to server
            writer.println(username);

            while (true) {

                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                writer.println(message);

                if (message.equalsIgnoreCase("/logout")) {
                    break;
                }
            }

            System.out.println("Disconnected.");

            socket.close();

        } catch (IOException e) {

            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}