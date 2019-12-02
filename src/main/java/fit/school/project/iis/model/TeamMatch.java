package fit.school.project.iis.model;

import lombok.Data;

@Data
public class TeamMatch {
    private Integer id_team_match;
    private Integer sets_home;
    private Integer sets_away;
    private Integer games_home;
    private Integer games_away;
    private Integer id_team_home;
    private Integer id_team_away;
    private Integer id_referee;
    private Integer id_tournament;

    public TeamMatch (Integer id_team_match, Integer sets_home, Integer sets_away, Integer games_home,
                        Integer games_away, Integer id_team_home, Integer id_team_away, Integer id_referee,
                        Integer id_tournament) {
        this.id_team_match = id_team_match;
        this.sets_home = sets_home;
        this.sets_away = sets_away;
        this.games_home = games_home;
        this.games_away = games_away;
        this.id_team_home = id_team_home;
        this.id_team_away = id_team_away;
        this.id_referee = id_referee;
        this.id_tournament = id_tournament;
    }
}
