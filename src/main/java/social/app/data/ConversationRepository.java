package social.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import social.app.domain.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
