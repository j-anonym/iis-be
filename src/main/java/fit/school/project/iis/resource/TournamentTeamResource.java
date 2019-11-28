package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.TournamentTeamMapper;
import fit.school.project.iis.model.TournamentTeam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/tournament")
public class TournamentTeamResource {
    private TournamentTeamMapper tournamentTeamMapper;

    public TournamentTeamResource(TournamentTeamMapper tournamentTeamMapper){
        this.tournamentTeamMapper = tournamentTeamMapper;
    }

    @RequestMapping(value = "/getpending/{id_tournament}", method= RequestMethod.GET)
    public @ResponseBody List<TournamentTeam> getPendingTeams(@PathVariable("id_tournament") int id_tournament) {
        return tournamentTeamMapper.getPendingTeams(id_tournament);
    }

    @RequestMapping(value = "/getaccepted/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<TournamentTeam> getAcceptedTeams(@PathVariable("id_tournament") int id_tournament) {
        return tournamentTeamMapper.getAcceptedTeams(id_tournament);
    }

    @RequestMapping(value = "/join/{id_tournament}/{id_team}", method=RequestMethod.GET)
    public void insertNewTournament(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_team") int id_team) {
        tournamentTeamMapper.joinTournament(id_tournament, id_team);
    }

    @RequestMapping(value = "/accept/{id_tournament}/{id_team}", method=RequestMethod.GET)
    public void acceptTeam(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_team") int id_team) {
        tournamentTeamMapper.acceptTeam(id_tournament, id_team);
    }

    @RequestMapping(value = "/decline/{id_tournament}/{id_team}", method=RequestMethod.GET)
    public void declineTeam(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_team") int id_team) {
        tournamentTeamMapper.declineTeam(id_tournament, id_team);
    }
}
