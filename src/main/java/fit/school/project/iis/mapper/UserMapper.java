package fit.school.project.iis.mapper;

import fit.school.project.iis.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users;")
    List<User> findAll();

}