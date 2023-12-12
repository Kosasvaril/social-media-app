package social.app.storage;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.github.cliftonlabs.json_simple.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.app.converter.JsonConversationConverter;
import social.app.converter.JsonMessageConverter;
import social.app.converter.JsonPostConverter;
import social.app.converter.JsonUserConverter;
import social.app.domain.*;
import social.app.service.PostService;
import social.app.service.UserService;

import java.io.*;
import java.util.List;

@Component
public class S3Bucket {
    private final String bucketName = "springsocialapp";
    private final Regions bucketRegion = Regions.US_EAST_1;
    private final AmazonS3 s3;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    private final JsonUserConverter jsonUserConverter;
    private final JsonPostConverter jsonPostConverter;

    private JsonMessageConverter jsonMessageConverter;
    private JsonConversationConverter jsonConversationConverter;

    public Regions getBucketRegion(){
        return Regions.US_EAST_1;
    }
    public String getBucketName(){
        return "springsocialapp";
    }
    public S3Bucket() throws IOException, JsonException {
        jsonMessageConverter = new JsonMessageConverter();
        jsonConversationConverter = new JsonConversationConverter();
        jsonUserConverter = new JsonUserConverter();
        jsonPostConverter = new JsonPostConverter();
        s3 = AmazonS3ClientBuilder.standard().withRegion(bucketRegion).build();
    }

    public void updatePosts(){
        List<Post> posts = postService.getAllPost();
        File file = new File("Posts.json");
        try{
            jsonPostConverter.updatePosts(posts);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        s3.putObject(bucketName, "posts/Posts.json", file);
    }
    public void updateConversations(){
        List<Conversation> conversations = userService.getConversations();
        List<Message> messages = userService.getMessages();
        File file = new File("Conversations.json");
        File file2 = new File("Messages.json");

        try{
            jsonConversationConverter.convert(conversations);
            jsonMessageConverter.convert(messages);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //s3.putObject(bucketName, "conversations/Conversations.json", file);
        //s3.putObject(bucketName, "messages/Messages.json", file2);
    }
    public void getAllPost(){
        try {
            readObject(
                    this.s3.getObject(bucketName, "posts/Posts.json"),
                    "Posts.json"
            );
            List<Post> posts = jsonPostConverter.readPosts(userService);
            for (Post post: posts) {
                postService.addNewPost(post);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void getAllUser(){
        try {
            readObject(
                    this.s3.getObject(bucketName, "users/Users.json"),
                    "Users.json"
            );
            List<User> users = jsonUserConverter.readUserFromJson();
            for(User user : users){
                userService.addNewUser(user);
            }
        }catch (IOException | NullPointerException e){
            System.out.println(e.getMessage());
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUsers() throws IOException {
        List<User> users = userService.getAllUsers();
        File file = new File(jsonUserConverter.getPATH());
        try{
            jsonUserConverter.createUpdateModel(users);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        s3.putObject(bucketName, "users/Users.json", file);
    }

    public void readObject(S3Object s3Object, String filename) throws IOException {
        S3ObjectInputStream s3is = s3Object.getObjectContent();
        FileOutputStream fos = new FileOutputStream(filename, false);
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0){
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.close();
    }
}
