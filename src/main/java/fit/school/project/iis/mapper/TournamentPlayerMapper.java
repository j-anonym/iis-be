package fit.school.project.iis.mapper;

import fit.school.project.iis.model.TournamentPlayer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Component
@Mapper
public interface TournamentPlayerMapper {

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = false")
    List<TournamentPlayer> getPendingPlayers(@Param("id_tournament") int id_tournament);

    @Select("SELECT * FROM player_tournament WHERE id_tournament = ${id_tournament} AND is_confirmed = true")
    List<TournamentPlayer> getAcceptedPlayers(@Param("id_tournament") int id_tournament);

    @Insert("INSERT INTO player_tournaments(id_player, id_tournament, is_confirmed) VALUES(#{id_player}::integer, #{id_tournament}::integer, false)")
    void joinTournament(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Update("UPDATE player_tournaments SET is_confirmed=true WHERE id_player=${id_player} AND id_tournament=${id_tournament}")
    void acceptPlayer(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);

    @Delete("DELETE FROM player_tournaments WHERE id_player=${id_player} AND id_tournament=${id_tournament}")
    void declinePlayer(@Param("id_player") int id_player, @Param("id_tournament") int id_tournament);
}
