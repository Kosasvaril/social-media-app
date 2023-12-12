package social.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import social.app.data.ConversationRepository;
import social.app.data.MessageRepository;
import social.app.data.UserRepository;
import social.app.domain.Conversation;
import social.app.domain.Message;
import social.app.domain.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ConversationRepository conversationRepository;

    private final MessageRepository messageRepository;

    @Autowired
    public UserService(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public User addNewUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username){
        System.out.println("Request for user by username! --------------------------");
        return userRepository.findByUsername(username);
    }

    public String addFollower(User user, String username){

        User currentUser = getUserByUsername(username).orElseThrow();

        if(!Objects.equals(user.getUserID(), currentUser.getUserID())){
            currentUser.followOtherUser(user);
            user.addFollower(currentUser);
            userRepository.save(user);
            userRepository.save(currentUser);
            return "Success";
        }else{
            return "Invalid action! (Users are the same)";
        }
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username).orElseThrow();
    }

    public Conversation saveConversation(Conversation conversation){
        return this.conversationRepository.save(conversation);
    }

    public Optional<Conversation> getConversationById(Long Id){
        return this.conversationRepository.findById(Id);
    }

    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }

    public Optional<Message> getMessageById(Long id){
        return messageRepository.findById(id);
    }

    public List<Conversation> getConversations(){
        return conversationRepository.findAll();
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }
}
