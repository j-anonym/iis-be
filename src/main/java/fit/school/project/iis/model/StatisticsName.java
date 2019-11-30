package fit.school.project.iis.model;

import lombok.Data;

@Data
public class StatisticsName {
    private String name;
    private int id_stat;
    private int won_matches;
    private int lost_matches;
    private int won_sets;
    private int lost_sets;
    private int won_games;
    private int lost_games;

}

