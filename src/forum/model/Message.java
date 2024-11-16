package forum.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private int messageId;
    private String title;
    private String author;
    private String content;
    private LocalDateTime date;
    private int likes;

    public Message(int messageId, String title, String author, String content, LocalDateTime localDateTime) {
        this.messageId = messageId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = LocalDateTime.now();
        this.likes = 0;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int addLike() {
        this.likes++;
        return this.likes;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId
                +
                ", title='" + title + '\''
                +
                ", author='" + author + '\''
                +
                ", content='" + content + '\''
                +
                ", date=" + date
                +
                ", likes=" + likes
                +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return messageId == message.messageId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }
}