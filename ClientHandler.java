import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private String username;

    public ClientHandler(
            Socket socket,
            String username
    ) {

        this.socket = socket;
        this.username = username;

        try {

            this.writer =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true
                    );

        } catch (IOException e) {

            System.out.println(
                    "Error creating writer: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public void run() {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            System.out.println(
                    username + " joined the chat."
            );

            ChatServer.broadcast(
                    "[SERVER] "
                            + username
                            + " joined the chat."
            );

            String message;

            while ((message = reader.readLine()) != null) {

                // LOGOUT
                if (message.equalsIgnoreCase("/logout")) {

                    ChatServer.removeUser(username);

                    ChatServer.broadcast(
                            "[SERVER] "
                                    + username
                                    + " left the chat."
                    );

                    socket.close();
                    break;
                }

                // HELP
                if (message.equalsIgnoreCase("/help")) {

                    send(
                            "\nAvailable Commands:\n"
                                    + "--------------------------------\n"
                                    + "/help\n"
                                    + "/users\n"
                                    + "/join roomName\n"
                                    + "/dm username message\n"
                                    + "/logout\n"
                                    + "--------------------------------"
                    );

                    continue;
                }

                // USERS
                if (message.equalsIgnoreCase("/users")) {

                    send(
                            ChatServer.getOnlineUsers()
                    );

                    continue;
                }

                // JOIN ROOM
                if (message.startsWith("/join ")) {

                    String[] parts =
                            message.split(" ", 2);

                    if (parts.length < 2) {

                        send(
                                "Usage: /join roomName"
                        );

                        continue;
                    }

                    String room =
                            parts[1].trim();

                    ChatServer.setUserRoom(
                            username,
                            room
                    );

                    send(
                            "You joined room: "
                                    + room
                    );

                    continue;
                }

                // PRIVATE MESSAGE
                if (message.startsWith("/dm ")) {

                    String[] parts =
                            message.split(" ", 3);

                    if (parts.length < 3) {

                        send(
                                "Usage: /dm username message"
                        );

                        continue;
                    }

                    String targetUser =
                            parts[1];

                    String privateMessage =
                            parts[2];

                    ClientHandler target =
                            ChatServer.getUser(
                                    targetUser
                            );

                    if (target == null) {

                        send(
                                "User not online: "
                                        + targetUser
                        );

                        continue;
                    }

                    target.send(
                            "(DM from "
                                    + username
                                    + "): "
                                    + privateMessage
                    );

                    send(
                            "(DM sent to "
                                    + targetUser
                                    + ")"
                    );

                    continue;
                }

                // NORMAL CHAT MESSAGE
                String formattedMessage =
                        username
                                + ": "
                                + message;

                System.out.println(
                        formattedMessage
                );

                ChatServer.broadcast(
                        formattedMessage
                );
            }

        } catch (IOException e) {

            System.out.println(
                    username + " disconnected."
            );
        }
    }

    public void send(
            String message
    ) {

        writer.println(message);
    }
}