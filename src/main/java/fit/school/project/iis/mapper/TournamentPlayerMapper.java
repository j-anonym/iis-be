package fit.school.project.iis.mapper;

import fit.school.project.iis.model.TournamentPlayer;
import fit.school.project.iis.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Component
@Mapper
public interface TournamentPlayerMapper {

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament}")
    List<TournamentPlayer> getAll(@Param("id_tournament") int id_tournament);

    @Select("SELECT users.id_user, users.id_stat, users.username, users.name, users.surname, users.birth, users.sex, users.nationality, users.is_admin, users.is_left_handed FROM player_tournament JOIN users ON player_tournament.id_player = users.id_user WHERE id_tournament = ${id_tournament}\n" +
            "  AND is_confirmed = false\n" +
            "  AND type = 'P'")
    List<User> getPendingPlayers(@Param("id_tournament") int id_tournament);

    @Select("SELECT users.id_user, users.id_stat, users.username, users.name, users.surname, users.birth, users.sex, users.nationality, users.is_admin, users.is_left_handed FROM player_tournament JOIN users ON player_tournament.id_player = users.id_user WHERE id_tournament = ${id_tournament}\n" +
            "  AND is_confirmed = false\n" +
            "  AND type = 'R'")
    List<User> getPendingReferees(@Param("id_tournament") int id_tournament);

    @Select("SELECT users.id_user, users.id_stat, users.username, users.name, users.surname, users.birth, users.sex, users.nationality, users.is_admin, users.is_left_handed FROM player_tournament JOIN users ON player_tournament.id_player = users.id_user WHERE id_tournament = ${id_tournament}\n" +
            "  AND is_confirmed = true\n" +
            "  AND type = 'P'")
    List<User> getAcceptedPlayers(@Param("id_tournament") int id_tournament);

    @Select("SELECT users.id_user, users.id_stat, users.username, users.name, users.surname, users.birth, users.sex, users.nationality, users.is_admin, users.is_left_handed FROM player_tournament JOIN users ON player_tournament.id_player = users.id_user WHERE id_tournament = ${id_tournament}\n" +
            "  AND is_confirmed = true\n" +
            "  AND type = 'R'")
    List<User> getAcceptedReferees(@Param("id_tournament") int id_tournament);

    @Insert("INSERT INTO player_tournament(id_player, type, id_tournament, is_confirmed) VALUES(#{id_player}::integer, 'P', #{id_tournament}::integer, false)")
    void joinTournament(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Insert("INSERT INTO player_tournament(id_player, type, id_tournament, is_confirmed) VALUES(#{id_player}::integer, 'R', #{id_tournament}::integer, false)")
    void joinTournamentReferee(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Update("UPDATE player_tournament t SET is_confirmed=true WHERE id_player=${id_player} AND t.id_tournament=${id_tournament}")
    void acceptPlayer(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Delete("DELETE FROM player_tournament t WHERE id_player=#{id_player}::integer AND t.id_tournament=#{id_tournament}::integer")
    void declinePlayer(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);
}
