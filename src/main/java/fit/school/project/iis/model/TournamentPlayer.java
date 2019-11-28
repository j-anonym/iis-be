package fit.school.project.iis.model;

import lombok.Data;

@Data
public class TournamentPlayer {
    private int id_player;
    private int id_tournament;
    private boolean is_confirmed;
}
