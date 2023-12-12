package social.app.domain;

import jakarta.persistence.*;

import java.util.List;

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
}
