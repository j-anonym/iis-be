package fit.school.project.iis.model;

import lombok.Data;

@Data
public class TournamentTeam {
    private int id_team;
    private int id_tournament;
    private boolean is_confirmed;
}
