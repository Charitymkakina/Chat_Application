import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void start(String username) {

        Scanner scanner = new Scanner(System.in);

        try {

            Socket socket = new Socket("localhost", 5000);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream())
                    );

            PrintWriter writer =
                    new PrintWriter(socket.getOutputStream(), true);

            // =========================
            // LOGIN
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
            // LISTENER THREAD (IMPORTANT FIX)
            // =========================
            new Thread(() -> {

                try {

                    String msg;

                    while ((msg = reader.readLine()) != null) {

                        System.out.println("\n" + msg);
                        System.out.print("Message: ");
                    }

                } catch (IOException e) {

                    System.out.println("Disconnected from server.");
                }

            }).start();

            // =========================
            // CHAT LOOP (SENDING ONLY)
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