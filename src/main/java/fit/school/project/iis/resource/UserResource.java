package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.UserMapper;
import fit.school.project.iis.model.User;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin(origins = "http://localhost:4200")
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

    @RequestMapping(value = "/delete/{id_user}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteUser(@PathVariable("id_user") int id_user) {
        userMapper.deleteUser(id_user);
    }
}
