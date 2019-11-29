package fit.school.project.iis.mapper;

import fit.school.project.iis.model.TournamentTeam;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TournamentTeamMapper {

    @Select("SELECT * FROM team_tournament WHERE id_tournament = ${id_tournament}")
    List<TournamentTeam> getAll(@Param("id_tournament") int id_tournament);

    @Select("SELECT * FROM team_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = false")
    List<TournamentTeam> getPendingTeams(@Param("id_tournament") int id_tournament);

    @Select("SELECT * FROM team_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = true")
    List<TournamentTeam> getAcceptedTeams(@Param("id_tournament") int id_tournament);

    @Insert("INSERT INTO team_tournament(id_team, id_tournament, is_confirmed) VALUES(#{id_team}::integer, #{id_tournament}::integer, false)")
    void joinTournament(@Param("id_team") int id_team, @Param("id_tournament") int id_tournament);

    @Update("UPDATE team_tournament SET is_confirmed=true WHERE id_team=${id_team} AND id_tournament=${id_tournament}")
    void acceptTeam(@Param("id_team") int id_team, @Param("id_tournament") int id_tournament);

    @Delete("DELETE FROM team_tournament WHERE id_team=${id_team} AND id_tournament=${id_tournament}")
    void declineTeam(@Param("id_team") int id_team, @Param("id_tournament") int id_tournament);
}
