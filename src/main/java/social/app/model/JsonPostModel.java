package social.app.model;

import com.github.cliftonlabs.json_simple.JsonObject;

public class JsonPostModel {

    private JsonObject userID;
    private JsonObject postID;
    private JsonObject title;
    private JsonObject content;
    public JsonPostModel(JsonObject userID, JsonObject postID, JsonObject title, JsonObject content) {
        this.userID = userID;
        this.postID = postID;
        this.title = title;
        this.content = content;
    }

    public JsonObject getUserID() {
        return userID;
    }

    public void setUserID(JsonObject userID) {
        this.userID = userID;
    }

    public JsonObject getPostID() {
        return postID;
    }

    public void setPostID(JsonObject postID) {
        this.postID = postID;
    }

    public JsonObject getTitle() {
        return title;
    }

    public void setTitle(JsonObject title) {
        this.title = title;
    }

    public JsonObject getContent() {
        return content;
    }

    public void setContent(JsonObject content) {
        this.content = content;
    }
}
