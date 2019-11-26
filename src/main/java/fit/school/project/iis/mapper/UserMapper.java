package fit.school.project.iis.mapper;

import fit.school.project.iis.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users;")
    List<User> findAll();

    @Delete("DELETE FROM users WHERE id_user = #{id_user}")
    void deleteUser(@Param("id_user") int id_user);

}