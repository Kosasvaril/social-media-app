package social.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostModel {
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;

    public PostModel(String title, String content) {
        this.title = title;
        this.content = content;
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
}
