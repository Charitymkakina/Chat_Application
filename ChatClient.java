import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

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

            // STEP 1: LOGIN
            System.out.println("LOGIN ");

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            writer.println(username + "," + password);

            String response = reader.readLine();

            if (!response.equals("LOGIN_SUCCESS")) {

                System.out.println("Login failed!");
                socket.close();
                return;
            }

            System.out.println("Login successful! You can chat now.");

            // STEP 2: CHAT LOOP
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

        scanner.close();
    }
}