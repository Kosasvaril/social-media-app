package social.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import social.app.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
