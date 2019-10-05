package fit.school.project.iis.mapper;

import fit.school.project.iis.model.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PersonMapper {

    @Select("SELECT * FROM persons;")
    List<Person> findAll();

}