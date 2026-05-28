import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ChatManager {

    private ArrayList<Message> messages;

    public ChatManager() {
        messages = new ArrayList<>();
    }

    public void sendMessage(String sender, String content) {

        Message message = new Message(sender, content);

        // store in memory
        messages.add(message);

        // save to file
        saveMessageToFile(message);
    }

    public void displayMessages() {

        if (messages.isEmpty()) {
            System.out.println("No messages available.");
            return;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Message message : messages) {

            String formattedTime =
                    message.getTimestamp().format(formatter);

            System.out.println(
                    "[" + formattedTime + "] "
                            + message.getSender()
                            + ": "
                            + message.getContent()
            );
        }
    }

    private void saveMessageToFile(Message message) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter("messages.txt", true)
                     )) {

            String formattedTime =
                    message.getTimestamp().format(formatter);

            writer.write(
                    "[" + formattedTime + "] "
                            + message.getSender()
                            + ": "
                            + message.getContent()
            );

            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
}