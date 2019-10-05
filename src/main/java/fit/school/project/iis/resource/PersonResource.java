package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.PersonMapper;
import fit.school.project.iis.model.Person;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/person")
public class PersonResource {

    private PersonMapper personMapper;

    public PersonResource(PersonMapper personMapper){
        this.personMapper = personMapper;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> findAll() {
        return personMapper.findAll();
    }
}
