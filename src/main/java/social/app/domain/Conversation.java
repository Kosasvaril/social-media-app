package social.app.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;
    @Column
    private List<Long> users;

    public Conversation( List<Long> users) {
        this.users = users;
    }

    public Conversation(){

    }

    public List<Long> getUsers() {
        return users;
    }

    public Long getConversationId() {
        return conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(conversationId, that.conversationId) && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId, users);
    }
}
