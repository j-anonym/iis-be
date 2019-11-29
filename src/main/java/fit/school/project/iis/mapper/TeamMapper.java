package fit.school.project.iis.mapper;

import fit.school.project.iis.model.Team;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TeamMapper {

    @Select("SELECT * FROM teams")
    List<Team> getAllTeams();

    @Insert("INSERT INTO teams(name, logo, id_player_1) VALUES(#{name}, #{logo}, #{id_player_1}::integer)")
    void insertNewTeam(Team team);

    @Select("SELECT * FROM teams WHERE id_team = ${id_team}")
    Team getTeam(@Param("id_team") int id_team);

    @Select("SELECT * FROM teams WHERE id_player_1 = ${id_player_1} ORDER BY id_team DESC LIMIT 1")
    Team getLast(@Param("id_player_1") int id_player_1);
}
