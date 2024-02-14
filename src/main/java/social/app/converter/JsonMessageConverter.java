package social.app.converter;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import social.app.domain.Conversation;
import social.app.domain.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonMessageConverter {

    private final JsonObject mediaMessages = new JsonObject();

    public void convert(List<Message> messages) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("Messages.json"));
        JsonArray jsonMessages = new JsonArray();
        mediaMessages.put("messages", jsonMessages);

        for(Message message : messages) {
            JsonObject newMessage = new JsonObject();
            newMessage.put("messageID", message.getMessageId());
            newMessage.put("content", message.getContent());
            newMessage.put("conversationID", message.getConversation().getConversationId());
            newMessage.put("sender", message.getUser().getUserID());
            jsonMessages.add(newMessage);
        }
        Jsoner.serialize(mediaMessages, writer);
        writer.close();
    }


}
