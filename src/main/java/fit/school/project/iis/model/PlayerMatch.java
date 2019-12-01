package fit.school.project.iis.model;

import lombok.Data;

@Data
public class PlayerMatch {
    private Integer id_player_match;
    private Integer sets_home;
    private Integer sets_away;
    private Integer games_home;
    private Integer games_away;
    private Integer id_user_home;
    private Integer id_user_away;
    private Integer id_referee;
    private Integer id_tournament;
}
