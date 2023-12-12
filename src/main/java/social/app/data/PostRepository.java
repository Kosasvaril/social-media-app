package social.app.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.app.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
