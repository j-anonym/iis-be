package fit.school.project.iis.mapper;

import fit.school.project.iis.model.Team;
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

    @Select("SELECT teams.*\n" +
            "FROM team_tournament\n" +
            "         JOIN teams ON team_tournament.id_team = teams.id_team\n" +
            "WHERE id_tournament = ${id_tournament}\n" +
            "  AND is_confirmed = false")
    List<Team> getPendingTeams(@Param("id_tournament") int id_tournament);

    @Select("SELECT teams.*\n" +
            "FROM team_tournament\n" +
            "         JOIN teams ON team_tournament.id_team = teams.id_team\n" +
            "WHERE id_tournament = ${id_tournament}\n" +
            "  AND is_confirmed = true")
    List<Team> getAcceptedTeams(@Param("id_tournament") int id_tournament);

    @Insert("INSERT INTO team_tournament(id_team, id_tournament, is_confirmed) VALUES(#{id_team}::integer, #{id_tournament}::integer, false)")
    void joinTournament(@Param("id_tournament") int id_tournament, @Param("id_team") int id_team);

    @Update("UPDATE team_tournament t SET is_confirmed=true WHERE id_team=${id_team} AND t.id_tournament=${id_tournament}")
    void acceptTeam(@Param("id_tournament") int id_tournament, @Param("id_team") int id_team);

    @Delete("DELETE FROM team_tournament t WHERE id_team=${id_team} AND t.id_tournament=${id_tournament}")
    void declineTeam(@Param("id_tournament") int id_tournament, @Param("id_team") int id_team);
}
