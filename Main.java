import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        User user = new User(username);

        ChatManager chatManager = new ChatManager();

        int choice;

        do {

            System.out.println("\n=== CHAT MENU ===");
            System.out.println("1. Send Message");
            System.out.println("2. View Messages");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();

                    chatManager.sendMessage(
                            user.getUsername(),
                            message
                    );

                    System.out.println("Message sent successfully!");
                    break;

                case 2:

                    System.out.println("\n--- Chat History ---");
                    chatManager.displayMessages();
                    break;

                case 3:

                    System.out.println("Exiting application...");
                    break;

                default:

                    System.out.println("Invalid option.");
            }

        } while (choice != 3);

        scanner.close();
    }
}