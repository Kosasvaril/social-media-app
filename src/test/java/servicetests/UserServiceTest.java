package servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import social.app.data.ConversationRepository;
import social.app.data.MessageRepository;
import social.app.data.UserRepository;
import social.app.domain.Conversation;
import social.app.domain.Message;
import social.app.domain.User;
import social.app.service.UserService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    ConversationRepository conversationRepository = Mockito.mock(ConversationRepository.class);
    MessageRepository messageRepository = Mockito.mock(MessageRepository.class);
    UserService systemUnderTest;

    @BeforeEach
    public void setup(){
        systemUnderTest = new UserService(userRepository, conversationRepository, messageRepository);
    }

    @Nested
    class savingTests{
        @BeforeEach
        public void setup(){
            when(userRepository.save(any(User.class))).thenReturn(new User());
            when(conversationRepository.save(any(Conversation.class))).thenReturn(new Conversation());
            when(messageRepository.save(any(Message.class))).thenReturn(new Message());
        }
        @Test
        void serviceShouldNotChangeNewUser(){
            assertEquals(new User(),systemUnderTest.addNewUser(new User()));
        }

        @Test
        void serviceShouldNotChangeNewMessage(){
            assertEquals(new Message(),systemUnderTest.saveMessage(new Message()));
        }

        @Test
        void serviceShouldNotChangeNewConversation(){
            assertEquals(new Conversation(),systemUnderTest.saveConversation(new Conversation()));
        }
    }

    @Nested
    class deletingTests{
        UserRepository userRepository;

        @BeforeEach
        @Autowired
        public void setup(){}
        //@Test
        //void serviceShouldDeleteUser(){
        //    User user = new User("testUser", "testPwd", "testEmail", new ArrayList<>(),
        //            new ArrayList<>(), Role.USER, 1L);
        //    assertThrows(Exception.class,
        //            () ->{
        //        systemUnderTest.deleteUser(user);
        //    });
        //}
    }


}
