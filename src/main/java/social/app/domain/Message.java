package social.app.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    @Column
    private String content;
    @ManyToOne
    private User user;
    @ManyToOne
    private Conversation conversation;

    public Message(String content, Conversation conversation, User user) {
        this.content = content;
        this.conversation = conversation;
        this.user = user;
    }

    public Message(){

    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId) && Objects.equals(content, message.content) && Objects.equals(user, message.user) && Objects.equals(conversation, message.conversation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, content, user, conversation);
    }
}
