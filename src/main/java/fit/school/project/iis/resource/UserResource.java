package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.UserMapper;
import fit.school.project.iis.model.User;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin(origins = "https://iis-tennis.herokuapp.com")
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

    @RequestMapping(value = "/get/{id_user}", method = RequestMethod.GET)
    @ResponseBody
    public User findUser(@PathVariable("id_user") int id_user) {
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
    
       @RequestMapping(value = "/updatename/{id_user}/{name}", method= RequestMethod.PUT)
    public @ResponseBody void updateName(@PathVariable("id_user") Integer id_user, @PathVariable("name") String name) {
        userMapper.updateName(id_user, name);
    }

    @RequestMapping(value = "/updatesurname/{id_user}/{surname}", method= RequestMethod.PUT)
    public @ResponseBody void updateSurname(@PathVariable("id_user") Integer id_user, @PathVariable("surname") String surname) {
        userMapper.updateSurname(id_user, surname);
    }

    @RequestMapping(value = "/updatenationality/{id_user}/{nationality}", method= RequestMethod.PUT)
    public @ResponseBody void updateNationality(@PathVariable("id_user") Integer id_user, @PathVariable("nationality") String nationality) {
        userMapper.updateNationality(id_user, nationality);
    }

    @RequestMapping(value = "/updatebirth/{id_user}/{birth}", method= RequestMethod.PUT)
    public @ResponseBody void updateBirth(@PathVariable("id_user") Integer id_user, @PathVariable("birth") String birth) {
        userMapper.updateBirth(id_user, birth);
    }

    @RequestMapping(value = "/updatesex/{id_user}/{sex}", method= RequestMethod.PUT)
    public @ResponseBody void updateSex(@PathVariable("id_user") Integer id_user, @PathVariable("sex") String sex) {
        userMapper.updateSex(id_user, sex);
    }

    @RequestMapping(value = "/updatelefthanded/{id_user}/{is_left_handed}", method= RequestMethod.PUT)
    public @ResponseBody void updateLeftHanded(@PathVariable("id_user") Integer id_user, @PathVariable("is_left_handed") Boolean is_left_handed) {
        userMapper.updateLeftHanded(id_user, is_left_handed);
    }
    
    @RequestMapping(value = "/getplayer/{id_stat}", method = RequestMethod.GET)
    @ResponseBody
    public User getPlayer(@PathVariable("id_stat") int id_stat) {
        return userMapper.getPlayer(id_stat);
    }
}
