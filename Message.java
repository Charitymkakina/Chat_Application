import java.time.LocalDateTime;

public class Message {

    private String sender;
    private String content;
    private LocalDateTime timestamp;

    // For new messages
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // For messages loaded from file
    public Message(String sender,
                   String content,
                   LocalDateTime timestamp) {

        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}