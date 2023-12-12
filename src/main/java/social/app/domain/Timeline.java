package social.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.app.storage.S3Bucket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Timeline{

    public static Timeline timeline;
    private List<Long> postIDs;


    public void setAllowedPosts(List<Long> postIDs) {
        this.postIDs = postIDs;
    }

    public List<Long> getAllowedPosts() {
        return List.copyOf(postIDs);
    }

    public static Timeline getTimeline() {
        if(timeline == null){

            timeline = new Timeline();

        }
        return timeline;
    }

    private Timeline(){
        this.postIDs = new ArrayList<>();
    }


}
