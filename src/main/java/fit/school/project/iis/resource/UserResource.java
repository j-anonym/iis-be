package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.UserMapper;
import fit.school.project.iis.model.User;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/person")
public class UserResource {

    private UserMapper userMapper;

    public UserResource(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
