import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();

        while (true) {

            System.out.println("\n=== CHAT APPLICATION ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":

                    // =========================
                    // REGISTER USER
                    // =========================
                    System.out.print("Enter username: ");
                    String regUser = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String regPass = scanner.nextLine();

                    userManager.registerUser(regUser, regPass);

                    break;

                case "2":

                    // =========================
                    // LOGIN USER
                    // =========================
                    System.out.print("Username: ");
                    String loginUser = scanner.nextLine();

                    System.out.print("Password: ");
                    String loginPass = scanner.nextLine();

                    boolean success =
                            userManager.loginUser(loginUser, loginPass);

                    if (success) {

                        System.out.println("Login successful!");

                        // Start Chat Client after login
                        ChatClient.main(new String[]{});

                    } else {
                        System.out.println("Login failed!");
                    }

                    break;

                case "3":

                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:

                    System.out.println("Invalid option!");
            }
        }
    }
}