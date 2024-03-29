package social.app.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(user, post.user) && Objects.equals(postID, post.postID) && Objects.equals(title, post.title) && Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, postID, title, content);
    }
}
