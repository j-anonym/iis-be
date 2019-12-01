package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.TournamentPlayerMapper;
import fit.school.project.iis.model.TournamentPlayer;
import fit.school.project.iis.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/api/tournament/player")
public class TournamentPlayerResource {

    private TournamentPlayerMapper tournamentPlayerMapper;

    public TournamentPlayerResource(TournamentPlayerMapper tournamentPlayerMapper){
        this.tournamentPlayerMapper = tournamentPlayerMapper;
    }

    @RequestMapping(value = "/getall/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<TournamentPlayer> getAll(@PathVariable("id_tournament") int id_tournament) {
        return tournamentPlayerMapper.getAll(id_tournament);
    }

    @RequestMapping(value = "/getpending/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<User> getPendingPlayers(@PathVariable("id_tournament") int id_tournament) {
        return tournamentPlayerMapper.getPendingPlayers(id_tournament);
    }

    @RequestMapping(value = "/getpendingreferee/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<User> getPendingReferees(@PathVariable("id_tournament") int id_tournament) {
        return tournamentPlayerMapper.getPendingReferees(id_tournament);
    }

    @RequestMapping(value = "/getaccepted/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<User> getAcceptedPlayers(@PathVariable("id_tournament") int id_tournament) {
        return tournamentPlayerMapper.getAcceptedPlayers(id_tournament);
    }

    @RequestMapping(value = "/getacceptedreferees/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<User> getAcceptedReferees(@PathVariable("id_tournament") int id_tournament) {
        return tournamentPlayerMapper.getAcceptedReferees(id_tournament);
    }

    @RequestMapping(value = "/join/{id_tournament}/{id_player}", method=RequestMethod.GET)
    public void joinTournament(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_player") int id_player) {
        tournamentPlayerMapper.joinTournament(id_tournament, id_player);
    }

    @RequestMapping(value = "/joinreferee/{id_tournament}/{id_player}", method=RequestMethod.GET)
    public void joinTournamentReferee(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_player") int id_player) {
        tournamentPlayerMapper.joinTournamentReferee(id_tournament, id_player);
    }

    @RequestMapping(value = "/accept/{id_tournament}/{id_player}", method=RequestMethod.PUT)
    public void acceptPlayer(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_player") int id_player) {
        tournamentPlayerMapper.acceptPlayer(id_player, id_tournament);
    }

    @RequestMapping(value = "/decline/{id_tournament}/{id_player}", method=RequestMethod.DELETE)
    public void declinePlayer(@PathVariable("id_tournament") int id_tournament, @PathVariable("id_player") int id_player) {
        tournamentPlayerMapper.declinePlayer(id_player, id_tournament);
    }
}
