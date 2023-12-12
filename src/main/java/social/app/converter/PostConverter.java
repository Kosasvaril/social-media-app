package social.app.converter;

import social.app.domain.Post;
import social.app.model.PostModel;

public class PostConverter{

    public Post convert(PostModel postModel) {
        return new Post(postModel.getTitle(), postModel.getContent());
    }
}
