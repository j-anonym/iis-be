package fit.school.project.iis.mapper;

import fit.school.project.iis.model.Tournament;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TournamentMapper {

    @Insert("INSERT INTO tournaments(prize, name, date_from, date_to, place, occupation, cost, capacity, is_singles, type, sponsors, id_staff) " +
            "VALUES(#{prize}, #{name}, #{date_from}::date, #{date_to}::date, #{place}, #{occupation}, #{cost}, #{capacity}, #{is_singles}, #{type}::player_type, #{sponsors}, 1)")
    void insertNewTournament(Tournament tournament);

    @Select("SELECT id_tournament FROM tournaments WHERE id_staff = 1 ORDER BY id_tournament DESC LIMIT 1")
    int getLastCreatedTournament(int id_staff);

    @Select("SELECT * FROM tournaments WHERE id_staff = ${id_user};")
    List<Tournament> getAllTournamentsByUser(@Param("id_user") int id_user);

    @Select("SELECT * FROM tournaments ORDER BY date_from DESC;")
    List<Tournament> getAllTournaments();

}
