package fit.school.project.iis.mapper;

import fit.school.project.iis.model.Team;
import org.apache.ibatis.annotations.*;
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
    int getLast(@Param("id_player_1") int id_player_1);

    @Update("UPDATE teams SET id_player_2=${id_player} WHERE id_team=${id_team}")
    void joinTeam(@Param("id_team") int id_team, @Param("id_player") int id_player);

    @Delete("DELETE from teams WHERE id_team=${id_team}")
    void deleteTeam(@Param("id_team") int id_team);

    @Select("SELECT * from teams WHERE id_player_1=${id_player} OR id_player_2=${id_player}")
    List<Team> getJoined(@Param("id_player") int id_player);
}
