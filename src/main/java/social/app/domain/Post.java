package social.app.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
@Table(name = "POST")
public class Post {

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @Column
    private String title;
    @Column
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public Post() {
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPostID() {
        return postID;
    }

    @Override
    public String toString() {
        return "{" +
                "  user:" + user +
                ", postID:" + postID +
                ", title:'" + title + '\'' +
                ", content:'" + content + '\'' +
                "} |";
    }
}
