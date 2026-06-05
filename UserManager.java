import java.io.*;
import java.util.ArrayList;

public class UserManager {

    private ArrayList<String> users;

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    // REGISTER USER (with hashed password)
    public boolean registerUser(String username, String password) {

        if (userExists(username)) {
            System.out.println("Username already exists!");
            return false;
        }

        String hashedPassword =
                PasswordUtil.hashPassword(password);

        String userData =
                "username=" + username
                        + ";password=" + hashedPassword;

        users.add(userData);
        saveUserToFile(userData);

        System.out.println("User registered successfully!");
        return true;
    }

    // LOGIN USER (DEBUG VERSION)
    public boolean loginUser(String username, String password) {

        System.out.println("\n=== LOGIN DEBUG ===");
        System.out.println("Attempting login for: " + username);

        String hashedInput =
                PasswordUtil.hashPassword(password);

        System.out.println("Loaded users: " + users.size());

        for (String user : users) {

            System.out.println("Checking user record: " + user);

            String[] fields = user.split(";");

            if (fields.length < 2) {
                continue;
            }

            String storedUsername =
                    fields[0].split("=")[1];

            String storedPassword =
                    fields[1].split("=")[1];

            System.out.println("Stored Username: " + storedUsername);

            if (storedUsername.equals(username)
                    && storedPassword.equals(hashedInput)) {

                
                return true;
            }
        }

        System.out.println("Invalid username or password!");
        return false;
    }

    // OPTIONAL CLEAN METHOD
    public boolean isUserValid(String username, String password) {
        return loginUser(username, password);
    }

    // CHECK IF USER EXISTS
    private boolean userExists(String username) {

        for (String user : users) {

            String[] fields = user.split(";");

            if (fields.length < 1) {
                continue;
            }

            String storedUsername =
                    fields[0].split("=")[1];

            if (storedUsername.equals(username)) {
                return true;
            }
        }

        return false;
    }

    // SAVE USER TO FILE
    private void saveUserToFile(String userData) {

        File file = new File("users.txt");

        System.out.println("\n=== FILE DEBUG ===");
        System.out.println("Saving to: " + file.getAbsolutePath());

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(file, true)
                     )) {

            writer.write(userData);
            writer.newLine();

            System.out.println("User saved successfully!");

        } catch (IOException e) {

            System.out.println(
                    "Error saving user: " + e.getMessage()
            );
        }
    }

    // LOAD USERS FROM FILE
    private void loadUsers() {

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader("users.txt")
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {
                users.add(line);
            }

        } catch (IOException e) {

            System.out.println(
                    "No users found. Starting fresh."
            );
        }
    }
}