package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.UserMapper;
import fit.school.project.iis.model.User;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/person")
public class UserResource {

    private UserMapper userMapper;

    public UserResource(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @RequestMapping(value = "/{id_user}", method = RequestMethod.GET)
    @ResponseBody
    public User findUser(@PathVariable("/get/id_user") int id_user) {
        return userMapper.findUser(id_user);
    }

    @RequestMapping(value = "/delete/{id_user}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteUser(@PathVariable("id_user") int id_user) {
        userMapper.deleteUser(id_user);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody User json) {
        User user = new User(json.getId_user(), json.getId_stat(), json.getUsername(), json.getName(), json.getSurname(), json.getBirth(),
                json.getSex(), json.getNationality(), json.getIs_admin(), json.getIs_left_handed());
        int count = userMapper.updateUser(user);
        System.out.println(count);
    }

    @RequestMapping(value = "/getloggeduserid/{username}", method = RequestMethod.GET)
    public @ResponseBody
    int getLoggedUserId(@PathVariable("username") String username) {
        return userMapper.getLoggedUserId(username);
    }

    @RequestMapping(value = "/getloggeduseradminstatus/{username}", method = RequestMethod.GET)
    public @ResponseBody
    boolean getLoggedUserAdminStatus(@PathVariable("username") String username) {
        return userMapper.getLoggedUserAdminStatus(username);
    }
}
