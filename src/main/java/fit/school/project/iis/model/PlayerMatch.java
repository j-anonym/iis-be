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

    public PlayerMatch (Integer id_player_match, Integer sets_home, Integer sets_away, Integer games_home,
                         Integer games_away, Integer id_user_home, Integer id_user_away, Integer id_referee,
                         Integer id_tournament) {
        this.id_player_match = id_player_match;
        this.sets_home = sets_home;
        this.sets_away = sets_away;
        this.games_home = games_home;
        this.games_away = games_away;
        this.id_user_home = id_user_home;
        this.id_user_away = id_user_away;
        this.id_referee = id_referee;
        this.id_tournament = id_tournament;
    }
}
