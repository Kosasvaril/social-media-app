package social.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social.app.domain.Post;
import social.app.service.PostService;
import social.app.storage.S3Bucket;

import java.util.List;

@RestController
@RequestMapping("socialmedia/timeline")
public class TimelineController {

    @Autowired
    PostService postService;

    @Autowired
    private S3Bucket s3Bucket;
    private boolean called;


    @GetMapping
    public ResponseEntity<List<Post>> getAllPost(){
        if(!called){
            //s3Bucket.getAllUser();
            //s3Bucket.getAllPost();
            called = true;
            System.out.println("Called");
        }
        List<Post> result = postService.getAllPost();
        return ResponseEntity.ok(result);
    }
}
