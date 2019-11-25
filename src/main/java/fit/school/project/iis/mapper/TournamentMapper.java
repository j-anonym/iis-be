package fit.school.project.iis.mapper;

import fit.school.project.iis.model.Tournament;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface TournamentMapper {

    @Insert("INSERT INTO tournaments(prize, name, date_from, date_to, place, occupation, cost, capacity, is_singles, type, sponsors, id_staff) " +
            "VALUES(#{prize}, #{name}, #{date_from}, #{date_to}, #{place}, #{occupation}, #{cost}, #{capacity}, #{is_singles}, #{type}, #{sponsors}, #{id_staff})")
    void insertNewTournament(Tournament tournament);
}
