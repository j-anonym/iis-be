package fit.school.project.iis.model;

import lombok.Data;

@Data
public class Team {
    private int id_team;
    private String name;
    private String logo;
    private Integer id_stat;
    private Integer id_player_1;
    private Integer id_player_2;

    public Team (int id_team, String name, String logo, Integer id_stat, Integer id_player_1, Integer id_player_2) {
        this.id_team = id_team;
        this.name = name;
        this.logo = logo;
        this.id_stat = id_stat;
        this.id_player_1 = id_player_1;
        this.id_player_2 = id_player_2;
    }
}
