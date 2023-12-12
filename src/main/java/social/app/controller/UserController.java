package social.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.app.config.JwtService;
import social.app.model.MessageModel;
import social.app.domain.Conversation;
import social.app.domain.Message;
import social.app.domain.User;
import social.app.service.UserService;
import social.app.storage.S3Bucket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;


    @Autowired
    private S3Bucket s3Bucket;

    @PostMapping("/profile/{id}")
    public ResponseEntity<String> followUser(
            @PathVariable("id") Long id,
            @RequestHeader(value = "Authorization") String token
    ){
        String tokenValue = token.substring(7);
        System.out.println(tokenValue);
        String username = jwtService.extractUsername(tokenValue);
        User targetUser = userService.getUserById(id).orElseThrow();
        String message = userService.addFollower(targetUser, username);

        try {
            s3Bucket.updateUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(message);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> viewUser(@PathVariable("id") Long id){
        User user = userService.getUserById(id).orElseThrow();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/profile/conversation/{userID}")
    public ResponseEntity<Conversation> startConversation(
            @RequestBody MessageModel messageModel,
            @PathVariable("userID") Long toUserId,
            @RequestHeader(value = "Authorization") String token)
    {
        User user = getUser(token);

        Conversation conversation = createConversation(toUserId, user);

        Message message = new Message(messageModel.getContent(), conversation, user);
        userService.saveMessage(message);


        return ResponseEntity.ok(conversation);
    }

    private Conversation createConversation(Long toUserId, User user) {
        List<Long> users = new ArrayList<>();
        users.add(user.getUserID());
        users.add(toUserId);
        Conversation conversation = new Conversation(users);
        conversation = userService.saveConversation(conversation);
        return conversation;
    }

    private User getUser(String token) {
        String tokenValue = token.substring(7);
        String username = jwtService.extractUsername(tokenValue);
        User user = userService.getUserByUsername(username).orElseThrow();
        return user;
    }

    @PostMapping("/conversation/{conversationID}")
    public ResponseEntity<Conversation> continueConversation(
            @RequestBody MessageModel messageModel,
            @PathVariable("conversationID") Long conversationID,
            @RequestHeader(value = "Authorization") String token
    ){
        Conversation conversation =
                userService.getConversationById(conversationID).orElseThrow();

        User user = getUser(token);

        Message message = new Message(messageModel.getContent(), conversation, user);
        userService.saveMessage(message);
        s3Bucket.updateConversations();
        return ResponseEntity.ok(conversation);
    }
}
