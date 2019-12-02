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

    @Select("SELECT id_user, id_stat, username, name, surname, birth, sex, nationality, is_admin, is_left_handed " +
            "FROM users WHERE id_user > 1;")
    List<User> findAll();

    @Delete("DELETE FROM users WHERE id_user = #{id_user}")
    void deleteUser(@Param("id_user") int id_user);

    @Select("SELECT  id_user, id_stat,username, name, surname, birth, sex, nationality, is_admin, is_left_handed " +
            "FROM users where id_user = #{id_user};")
    User findUser(@Param("id_user") int id_user);

    @Update("UPDATE users SET name = #{name}, surname = #{surname}, birth = #{birth}::date + '1 day'::interval, sex = #{sex}::player_type, " +
            "nationality = UPPER(#{nationality}), is_admin = #{is_admin}, is_left_handed = #{is_left_handed}" +
            "WHERE id_user = #{id_user}; ")
    int updateUser(User user);

    @Select("SELECT id_user FROM users WHERE username = #{username}")
    int getLoggedUserId(@Param("username") String username);

    @Select("SELECT is_admin FROM users WHERE username = #{username}")
    boolean getLoggedUserAdminStatus(@Param("username") String username);
    
    @Update("UPDATE users SET name=#{name} WHERE id_user=${id_user}")
    void updateName(@Param("id_user") Integer id_user, @Param("name") String name);

    @Update("UPDATE users SET surname=#{surname} WHERE id_user=${id_user}")
    void updateSurname(@Param("id_user") Integer id_user, @Param("surname") String surname);

    @Update("UPDATE users SET nationality=#{nationality} WHERE id_user=${id_user}")
    void updateNationality(@Param("id_user") Integer id_user, @Param("nationality") String nationality);

    @Update("UPDATE users SET birth=#{birth}::date WHERE id_user=${id_user}")
    void updateBirth(@Param("id_user") Integer id_user, @Param("birth") String birth);

    @Update("UPDATE users SET sex=#{sex}::player_type WHERE id_user=${id_user}")
    void updateSex(@Param("id_user") Integer id_user, @Param("sex") String sex);

    @Update("UPDATE users SET is_left_handed=#{is_left_handed} WHERE id_user=${id_user}")
    void updateLeftHanded(@Param("id_user") Integer id_user, @Param("is_left_handed") Boolean is_left_handed);
    
    @Select("SELECT  id_user, id_stat,username, name, surname, birth, sex, nationality, is_admin, is_left_handed " +
            "FROM users where id_stat = #{id_stat};")
    User getPlayer(@Param("id_stat") int id_stat);

    //@Select("SELECT id_tournament FROM tournaments WHERE id_staff = 1 ORDER BY id_tournament DESC LIMIT 1")
    //int getLastCreatedTournament(int id_staff);
}
