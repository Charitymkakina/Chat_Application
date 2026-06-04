import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    // NEW ENTRY POINT USED BY Main.java
    public static void start(String username) {

        Scanner scanner = new Scanner(System.in);

        try {

            Socket socket =
                    new Socket("localhost", 5000);

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

            // =========================
            // LOGIN TO SERVER
            // =========================
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            writer.println(username + "," + password);

            String response = reader.readLine();

            if (!"LOGIN_SUCCESS".equals(response)) {

                System.out.println("Login failed!");
                socket.close();
                return;
            }

            System.out.println("Login successful! You can chat now.");

            // =========================
            // CHAT LOOP
            // =========================
            while (true) {

                System.out.print("Message: ");
                String message = scanner.nextLine();

                writer.println(message);

                if (message.equalsIgnoreCase("/logout")) {
                    break;
                }
            }

            socket.close();

        } catch (IOException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}