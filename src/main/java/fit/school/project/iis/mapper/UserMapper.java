package fit.school.project.iis.mapper;

import fit.school.project.iis.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Select("SELECT id_user, id_stat, name, surname, birth, sex, nationality, is_admin, is_left_handed " +
            "FROM users WHERE id_user > 1;")
    List<User> findAll();

    @Delete("DELETE FROM users WHERE id_user = #{id_user}")
    void deleteUser(@Param("id_user") int id_user);

    @Select("SELECT  id_user, id_stat, name, surname, birth, sex, nationality, is_admin, is_left_handed " +
            "FROM users where id_user = #{id_user};")
    User findUser(@Param("id_user") int id_user);

    @Update("UPDATE users SET name = #{name}, surname = #{surname}, birth = #{birth}::date, sex = #{sex}::player_type, " +
            "nationality = #{nationality}, is_admin = #{is_admin}, is_left_handed = #{is_left_handed}" +
            "WHERE id_user = #{id_user}; ")
    int updateUser(User user);
}
