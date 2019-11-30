package fit.school.project.iis.mapper;

import fit.school.project.iis.model.Tournament;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TournamentMapper {

    @Insert("INSERT INTO tournaments(prize, name, date_from, date_to, place, occupation, cost, capacity, is_singles, type, sponsors, id_staff) " +
            "VALUES(#{prize}, #{name}, #{date_from}::date + '1 day'::interval, #{date_to}::date + '1 day'::interval, #{place}, #{occupation}, #{cost}, #{capacity}, #{is_singles}, #{type}::player_type, #{sponsors}, ${id_staff})")
    void insertNewTournament(Tournament tournament);

    @Select("SELECT * FROM tournaments WHERE id_tournament = ${id_tournament}")
    Tournament getTournament(int id_tournament);

    @Select("SELECT id_tournament FROM tournaments WHERE id_staff = 1 ORDER BY id_tournament DESC LIMIT 1")
    int getLastCreatedTournament(int id_staff);

    @Select("select *\n" +
            "from tournaments\n" +
            "where id_tournament in (\n" +
            "    select id_tournament\n" +
            "    from player_tournament\n" +
            "    where id_player = ${id_user}\n" +
            "    union all\n" +
            "    select id_tournament\n" +
            "    from team_tournament\n" +
            "    where id_team in (\n" +
            "        select id_team\n" +
            "        from teams\n" +
            "        where id_player_1 = ${id_user}\n" +
            "           or id_player_2 = ${id_user})\n" +
            ")\n" +
            "   or id_staff = ${id_user}\n" +
            ";")
    List<Tournament> getAllTournamentsByUser(@Param("id_user") int id_user);

    @Select("SELECT * FROM tournaments ORDER BY date_from DESC;")
    List<Tournament> getAllTournaments();

    @Delete("DELETE FROM tournaments WHERE id_tournament = ${id_tournament};")
    void deleteTournament(@Param("id_tournament") int id_tournament);

}
