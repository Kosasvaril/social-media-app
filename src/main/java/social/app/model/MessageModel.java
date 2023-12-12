package social.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageModel {
    @JsonProperty("content")
    private String content;

    public MessageModel(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    public MessageModel(){
    }

    public void setContent(String content) {
        this.content = content;
    }
}
