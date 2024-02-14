package social.app.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import social.app.config.JwtService;
import social.app.converter.PostConverter;
import social.app.domain.Post;
import social.app.domain.User;
import social.app.model.PostModel;
import social.app.service.PostService;
import social.app.service.UserService;
import social.app.storage.S3Bucket;

@RestController
@RequestMapping("socialmedia/post")
public class PostController extends HttpServlet {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @Autowired
    private S3Bucket s3Bucket;

    @GetMapping(path="/{id}")
    public ResponseEntity<Post> getPost(@PathVariable("id") Long id) {
        Post result = null;
        try {
            result = postService.getSinglePostByID(id).orElseThrow();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Post> addPost(HttpServletRequest httpServletRequest, @RequestBody PostModel postModel ){
        Cookie[] cookies = httpServletRequest.getCookies();
        String token = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("jwtToken")){
                    System.out.println(cookie);
                    token = cookie.getValue();
                }
            }
        }else {
            System.out.println("cookies null!");
        }

        PostConverter postConverter = new PostConverter();
        Post post = postConverter.convert(postModel);
        String username = jwtService.extractUsername(token);
        User user = (User) userService.loadUserByUsername(username);
        post.setUser(user);
        post = postService.addNewPost(post);
        //s3Bucket.updatePosts();

        return ResponseEntity.ok(post);
    }
}
