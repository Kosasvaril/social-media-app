package social.app.converter;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.springframework.beans.factory.annotation.Autowired;
import social.app.domain.Post;
import social.app.service.UserService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonPostConverter {
    private final String PATH = "Posts.json";
    private final JsonObject mediaPosts = new JsonObject();
    private JsonArray postsArray;

    public void updatePosts(List<Post> posts) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("Posts.json"));

        this.postsArray = new JsonArray();
        mediaPosts.put("postsArray",postsArray);
        for (Post post : posts) {
            createJsonPost(post);
        }
        Jsoner.serialize(mediaPosts, writer);
        writer.close();
    }

    private void createJsonPost(Post post) {
        JsonObject newPost = new JsonObject();
        newPost.put("title", post.getTitle());
        newPost.put("content", post.getContent());
        newPost.put("userID", post.getUser().getUserID());
        newPost.put("postID", post.getPostID());
        postsArray.add(newPost);
    }

    public List<Post> readPosts(UserService userService) throws IOException, JsonException {
        Reader reader = Files.newBufferedReader(Paths.get("Posts.json"));
        JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
        reader.close();
        List<Post> posts = new ArrayList<>();
        postsArray = (JsonArray) parser.get("postsArray");
        postsArray.forEach(entry -> {
            JsonObject post = (JsonObject) entry;
            Post newPost = createPostFromJson(post, userService);
            posts.add(newPost);
        });
        return posts;
    }

    private Post createPostFromJson(JsonObject post, UserService userService) {
        String title = (String) post.get("title");
        String content = (String) post.get("content");
        BigDecimal userID = (BigDecimal) post.get("userID");
        Long userIDLong = Long.parseLong(String.valueOf(userID));
        return new Post(userService.getUserById(userIDLong).orElseThrow(),title,content);
    }

    public String getPATH() {
        return PATH;
    }

}