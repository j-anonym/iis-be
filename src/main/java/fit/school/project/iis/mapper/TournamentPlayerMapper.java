package fit.school.project.iis.mapper;

import fit.school.project.iis.model.TournamentPlayer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Component
@Mapper
public interface TournamentPlayerMapper {

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament}")
    List<TournamentPlayer> getAll(@Param("id_tournament") int id_tournament);

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = false AND type = 'P'")
    List<TournamentPlayer> getPendingPlayers(@Param("id_tournament") int id_tournament);

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = false AND type = 'R'")
    List<TournamentPlayer> getPendingReferees(@Param("id_tournament") int id_tournament);

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = true AND type = 'P'")
    List<TournamentPlayer> getAcceptedPlayers(@Param("id_tournament") int id_tournament);

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = true AND type = 'R'")
    List<TournamentPlayer> getAcceptedReferees(@Param("id_tournament") int id_tournament);

    @Insert("INSERT INTO player_tournament(id_player, type, id_tournament, is_confirmed) VALUES(#{id_player}::integer, 'P', #{id_tournament}::integer, false)")
    void joinTournament(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Insert("INSERT INTO player_tournament(id_player, type, id_tournament, is_confirmed) VALUES(#{id_player}::integer, 'R', #{id_tournament}::integer, false)")
    void joinTournamentReferee(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Update("UPDATE player_tournament SET is_confirmed=true WHERE id_player=${id_player} AND id_tournament=${id_tournament}")
    void acceptPlayer(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Delete("DELETE FROM player_tournament WHERE id_player=#{id_player}::integer AND id_tournament=#{id_tournament}::integer")
    void declinePlayer(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);
}
