package social.app.model;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class JsonUserModel {

    private JsonObject username;
    private JsonObject password;
    private JsonObject email;
    private JsonArray followers;
    private JsonArray following;
    private JsonObject role;

    public JsonArray getFollowers() {
        return followers;
    }

    public void setFollowers(JsonArray followers) {
        this.followers = followers;
    }

    public JsonArray getFollowing() {
        return following;
    }

    public void setFollowing(JsonArray following) {
        this.following = following;
    }

    public JsonUserModel(JsonObject username, JsonObject password, JsonObject email, JsonArray followers, JsonArray following, JsonObject role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.followers = followers;
        this.following = following;
        this.role = role;
    }

    public JsonObject getUsername() {
        return username;
    }

    public void setUsername(JsonObject username) {
        this.username = username;
    }

    public JsonObject getPassword() {
        return password;
    }

    public void setPassword(JsonObject password) {
        this.password = password;
    }

    public JsonObject getEmail() {
        return email;
    }

    public void setEmail(JsonObject email) {
        this.email = email;
    }

    public JsonObject getRole() {
        return role;
    }

    public void setRole(JsonObject role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "JsonUserModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", role='" + role + '\'' +
                '}';
    }
}
