package social.app.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import social.app.auth.RegisterRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "MEDIA_USER")
public class User implements UserDetails{

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    private List<Long> followers;

    @Column
    private List<Long> following;

    public List<Long> getFollowers() {
        return followers;
    }

    public List<Long> getFollowing() {
        return following;
    }

    @Enumerated(EnumType.STRING)
    private Role role;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;


    public String roleToString(){
        if (Objects.requireNonNull(role) == Role.USER) {
            return "USER";
        }
        return "ADMIN";
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User(RegisterRequest request, String encodedPassword){
        username = request.getUsername();
        password = encodedPassword;
        email = request.getEmail();
        role = Role.USER;
        following = new ArrayList<>();
        followers = new ArrayList<>();
    }


    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        following = new ArrayList<>();
        followers = new ArrayList<>();
    }

    public User(String username, String password, String email, List<Long> followers, List<Long> following, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.followers = followers;
        this.following = following;
        this.role = role;
    }

    public User(){}

    public void addFollower(User user){
        followers.add(user.getUserID());
    }

    public void followOtherUser(User user){
        following.add(user.getUserID());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "{" +
                "  username:'" + username + '\'' +
                ", password:'" + password + '\'' +
                ", email:'" + email + '\'' +
                ", followers:" + followers +
                ", following:" + following +
                ", role:" + role +
                ", userID:" + userID +
                "} | \n";
    }
}
