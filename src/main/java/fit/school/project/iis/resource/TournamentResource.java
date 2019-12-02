package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.TournamentMapper;
import fit.school.project.iis.model.PlayerMatch;
import fit.school.project.iis.model.Team;
import fit.school.project.iis.model.TeamMatch;
import fit.school.project.iis.model.Tournament;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@CrossOrigin(origins = "https://iis-tennis.herokuapp.com")
@RequestMapping("/api/tournament")
public class TournamentResource {

    private TournamentMapper tournamentMapper;

    public TournamentResource(TournamentMapper tournamentMapper){
        this.tournamentMapper = tournamentMapper;
    }

    @RequestMapping(value = "/create", method={RequestMethod.GET, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertNewTournament(@RequestBody Tournament json) {

        Tournament tournament = new Tournament( -1,
                json.getPrize(), json.getName(), json.getDate_from(), json.getDate_to(), json.getPlace(), json.getOccupation(),
                json.getCost(), json.getCapacity(), json.is_singles(), json.getType(), json.getSponsors(), json.getId_staff()
        );

        tournamentMapper.insertNewTournament(tournament);
    }

    @RequestMapping(value = "/get/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody Tournament getTournament(@PathVariable("id_tournament") int id_tournament) {
        return tournamentMapper.getTournament(id_tournament);
    }

    @RequestMapping(value = "/getlast/{id_staff}", method=RequestMethod.GET)
    public @ResponseBody int getLastCreatedTournament(@PathVariable("id_staff") int id_staff) {
        return tournamentMapper.getLastCreatedTournament(id_staff);
    }

    @RequestMapping(value = "/getall", method=RequestMethod.GET)
    public @ResponseBody List<Tournament> getAllTournaments() {
        return tournamentMapper.getAllTournaments();
    }

    @RequestMapping(value = "/getall/{id_user}", method=RequestMethod.GET)
    public @ResponseBody List<Tournament> getAllTournamentsByUser(@PathVariable("id_user") int id_user) {
        return tournamentMapper.getAllTournamentsByUser(id_user);
    }

    @RequestMapping(value = "delete/{id_tournament}", method=RequestMethod.DELETE)
    public @ResponseBody void deleteTournament(@PathVariable("id_tournament") int id_tournament) {
        tournamentMapper.deleteTournament(id_tournament);
    }

    @RequestMapping(value = "/getplayermatches/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<PlayerMatch> getAllPlayerMatches(@PathVariable("id_tournament") int id_tournament) {
        return tournamentMapper.getAllPlayerMatches(id_tournament);
    }

    @RequestMapping(value = "/getteammatches/{id_tournament}", method=RequestMethod.GET)
    public @ResponseBody List<TeamMatch> getAllTeamMatches(@PathVariable("id_tournament") int id_tournament) {
        return tournamentMapper.getAllTeamMatches(id_tournament);
    }

    @RequestMapping(value = "/update/player/match", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void updatePlayerMatch(@RequestBody PlayerMatch json) {

        PlayerMatch playerMatch = new PlayerMatch(json.getId_player_match(), json.getSets_home(), json.getSets_away(),
                json.getGames_home(), json.getGames_away(), json.getId_user_home(), json.getId_user_away(),
                json.getId_referee(), json.getId_tournament());

        tournamentMapper.updatePlayerMatch(playerMatch);
    }

    @RequestMapping(value = "/update/team/match", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void updateTeamMatch(@RequestBody TeamMatch json) {

        TeamMatch teamMatch = new TeamMatch(json.getId_team_match(), json.getSets_home(), json.getSets_away(),
                json.getGames_home(), json.getGames_away(), json.getId_team_home(), json.getId_team_away(),
                json.getId_referee(), json.getId_tournament());

        tournamentMapper.updateTeamMatch(teamMatch);
    }
}
