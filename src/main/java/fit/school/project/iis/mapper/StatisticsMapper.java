package fit.school.project.iis.mapper;

import fit.school.project.iis.model.StatisticsName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface StatisticsMapper {

    @Select("SELECT name || ' ' || surname as name,\n" +
            "       s.id_stat,\n" +
            "       s.won_matches,\n" +
            "       s.lost_matches,\n" +
            "       s.won_sets,\n" +
            "       s.lost_sets,\n" +
            "       s.won_games,\n" +
            "       s.lost_games\n" +
            "from users\n" +
            "         JOIN statistics s on users.id_stat = s.id_stat\n" +
            "WHERE s.id_stat > 1;")
    List<StatisticsName> findAllPlayers();

    @Select("SELECT name as name,\n" +
            "       s.id_stat,\n" +
            "       s.won_matches,\n" +
            "       s.lost_matches,\n" +
            "       s.won_sets,\n" +
            "       s.lost_sets,\n" +
            "       s.won_games,\n" +
            "       s.lost_games\n" +
            "from teams\n" +
            "         JOIN statistics s on teams.id_stat = s.id_stat\n" +
            "WHERE s.id_stat > 1;")
    List<StatisticsName> findAllTeams();

}
