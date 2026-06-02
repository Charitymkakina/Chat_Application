import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;

public class ChatManager {

    private ArrayList<Message> messages;

    public ChatManager() {
        messages = new ArrayList<>();
        loadMessagesFromFile();
    }

    public void sendMessage(String sender, String content) {

        Message message = new Message(sender, content);

        messages.add(message);

        saveMessageToFile(message);
    }

    public void displayMessages() {

        if (messages.isEmpty()) {
            System.out.println("No messages available.");
            return;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < messages.size(); i++) {

            Message message = messages.get(i);

            String formattedTime =
                    message.getTimestamp().format(formatter);

            System.out.println(
                    "[" + i + "] "
                            + "[" + formattedTime + "] "
                            + message.getSender()
                            + ": "
                            + message.getContent()
            );
        }
    }

    public void searchMessages(String keyword) {

        boolean found = false;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Message message : messages) {

            if (message.getContent()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                String formattedTime =
                        message.getTimestamp().format(formatter);

                System.out.println(
                        "[" + formattedTime + "] "
                                + message.getSender()
                                + ": "
                                + message.getContent()
                );

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No messages found containing: "
                            + keyword
            );
        }
    }

    public void deleteMessage(int index) {

        if (index < 0 || index >= messages.size()) {

            System.out.println(
                    "Invalid message ID."
            );

            return;
        }

        messages.remove(index);

        System.out.println(
                "Message deleted successfully."
        );

        rewriteFile();
    }

    private void saveMessageToFile(Message message) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     "messages.txt",
                                     true
                             )
                     )) {

            writer.write(
                    "username="
                            + message.getSender()
                            + ";message="
                            + message.getContent()
                            + ";timestamp="
                            + message.getTimestamp()
            );

            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Error saving message: "
                            + e.getMessage()
            );
        }
    }

    private void rewriteFile() {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     "messages.txt"
                             )
                     )) {

            for (Message message : messages) {

                writer.write(
                        "username="
                                + message.getSender()
                                + ";message="
                                + message.getContent()
                                + ";timestamp="
                                + message.getTimestamp()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error updating file: "
                            + e.getMessage()
            );
        }
    }

    private void loadMessagesFromFile() {

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     "messages.txt"
                             )
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] fields =
                        line.split(";");

                if (fields.length == 3) {

                    String sender =
                            fields[0]
                                    .split("=")[1];

                    String content =
                            fields[1]
                                    .split("=")[1];

                    String timestamp =
                            fields[2]
                                    .split("=")[1];

                    Message message =
                            new Message(
                                    sender,
                                    content,
                                    LocalDateTime.parse(
                                            timestamp
                                    )
                            );

                    messages.add(message);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "No previous chat history found."
            );
        }
    }
}