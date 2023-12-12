package social.app.converter;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import social.app.domain.Conversation;
import social.app.domain.Post;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonConversationConverter {
    private JsonArray jsonConversations;
    private final JsonObject mediaConversations = new JsonObject();

    public void convert(List<Conversation> conversations) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("Conversations.json"));
        jsonConversations = new JsonArray();
        mediaConversations.put("conversations", jsonConversations);

        for(Conversation conversation : conversations) {
            JsonObject newConversation = new JsonObject();
            newConversation.put("conversationID", conversation.getConversationId());
            newConversation.put("users", List.of(new JsonArray(conversation.getUsers())));
            jsonConversations.add(newConversation);
        }
        Jsoner.serialize(mediaConversations, writer);
        writer.close();
    }
}
