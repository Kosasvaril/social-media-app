package social.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.app.data.PostRepository;
import social.app.domain.Post;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }
    public Optional<Post> getSinglePostByID(Long id){
        return postRepository.findById(id);
    }

    public Post addNewPost(Post post){
        return postRepository.save(post);
    }
}
