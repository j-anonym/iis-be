package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.TournamentTeamMapper;
import fit.school.project.iis.model.Team;
import fit.school.project.iis.model.TournamentTeam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@CrossOrigin(origins = "https://iis-tennis.herokuapp.com")
@RequestMapping("/api/tournament/team")
public class TournamentTeamResource {
    private TournamentTeamMapper tournamentTeamMapper;

    public TournamentTeamResource(TournamentTeamMapper tournamentTeamMapper){
        this.tournamentTeamMapper = tournamentTeamMapper;
    }

    @RequestMapping(value = "/getall/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<TournamentTeam> getAll(@PathVariable("id_tournament") int id_tournament) {
        return tournamentTeamMapper.getAll(id_tournament);
    }

    @RequestMapping(value = "/getpending/{id_tournament}", method= RequestMethod.GET)
    public @ResponseBody List<Team> getPendingTeams(@PathVariable("id_tournament") int id_tournament) {
        return tournamentTeamMapper.getPendingTeams(id_tournament);
    }

    @RequestMapping(value = "/getaccepted/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<Team> getAcceptedTeams(@PathVariable("id_tournament") int id_tournament) {
        return tournamentTeamMapper.getAcceptedTeams(id_tournament);
    }

    @RequestMapping(value = "/join/{id_tournament}/{id_team}", method=RequestMethod.GET)
    public void insertNewTournament(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_team") int id_team) {
        tournamentTeamMapper.joinTournament(id_tournament, id_team);
    }

    @RequestMapping(value = "/accept/{id_tournament}/{id_team}", method=RequestMethod.PUT)
    public void acceptTeam(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_team") int id_team) {
        tournamentTeamMapper.acceptTeam(id_tournament, id_team);
    }

    @RequestMapping(value = "/decline/{id_tournament}/{id_team}", method=RequestMethod.DELETE)
    public void declineTeam(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_team") int id_team) {
        tournamentTeamMapper.declineTeam(id_tournament, id_team);
    }
}
