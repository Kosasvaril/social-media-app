package social.app.converter;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import social.app.domain.Post;
import social.app.domain.Role;
import social.app.domain.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonUserConverter {
    private final JsonObject mediaUsers = new JsonObject();
    private JsonArray usersArray;
    private final String PATH = "Users.json";

    public JsonUserConverter() throws IOException, JsonException {
        this.usersArray = new JsonArray();
    }
    public void createUpdateModel(List<User> users) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(PATH));
        this.usersArray = new JsonArray();
        mediaUsers.put("usersArray", usersArray);
        for (User user : users) {
            JsonObject newUser = new JsonObject();
            newUser.put("username",user.getUsername());
            newUser.put("password",user.getPassword());
            newUser.put("email",user.getEmail());
            newUser.put("followers", List.of(new JsonArray(user.getFollowers())));
            newUser.put("following",List.of(new JsonArray(user.getFollowing())));
            newUser.put("role", user.roleToString());
            usersArray.add(newUser);
        }
        Jsoner.serialize(mediaUsers, writer);
        writer.close();
    }

    public List<User> readUserFromJson() throws IOException, JsonException {
        Reader reader = Files.newBufferedReader(Path.of(PATH));
        JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
        reader.close();

        this.usersArray = (JsonArray) parser.get("usersArray");
        List<User> users = new ArrayList<>();
        usersArray.forEach(entry -> {
            JsonObject user = (JsonObject) entry;
            String username = (String) user.get("username");
            String password = (String) user.get("password");
            String email = (String) user.get("email");

            List<Long> followers = new ArrayList<>();
            Object jsonFollowers = user.get("followers");
            String tmpFollowers = jsonFollowers.toString();
            tmpFollowers = tmpFollowers.replace("[","");
            tmpFollowers = tmpFollowers.replace("]","");
            tmpFollowers = tmpFollowers.replaceAll("\\s+","");
            if(tmpFollowers.contains(",")){
                String[] followingIDs = tmpFollowers.split(",");
                for (String ID: followingIDs) {
                    followers.add(Long.parseLong(ID));
                }
            }else if(!tmpFollowers.isEmpty()){
                followers.add(Long.parseLong(tmpFollowers));
            }

            List<Long> following = new ArrayList<>();
            Object jsonFollowing = user.get("following");
            String tmpFollowings = jsonFollowing.toString();
            tmpFollowings = tmpFollowings.replace("[","");
            tmpFollowings = tmpFollowings.replace("]","");
            tmpFollowings = tmpFollowings.replaceAll("\\s+","");
            if(tmpFollowings.contains(",")){
                String[] followingIDs = tmpFollowings.split(",");
                for (String ID: followingIDs) {

                    following.add(Long.parseLong(ID));
                }
            }else if(!tmpFollowings.isEmpty()){
                following.add(Long.parseLong(tmpFollowings));
            }

            Role role = (user.get("role").equals("USER") ? Role.USER : Role.ADMIN );
            User newUser = new User(username, password, email, followers, following, role);
            users.add(newUser);
        });
        return users;
    }

    public String getPATH() {
        return PATH;
    }
}
