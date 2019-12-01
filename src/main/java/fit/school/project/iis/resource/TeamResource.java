package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.TeamMapper;
import fit.school.project.iis.model.Team;
import fit.school.project.iis.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/team")
public class TeamResource {
    private TeamMapper teamMapper;

    public TeamResource(TeamMapper teamMapper) { this.teamMapper = teamMapper; }

    @RequestMapping(value = "/getall", method= RequestMethod.GET)
    public @ResponseBody List<Team> getAll() {
        return teamMapper.getAllTeams();
    }

    @RequestMapping(value = "/get/{id_team}", method= RequestMethod.GET)
    public @ResponseBody Team getTeam(@PathVariable("id_team") int id_team) {
        return teamMapper.getTeam(id_team);
    }

    @RequestMapping(value = "/get/players/{id_team}", method = RequestMethod.GET)
    public @ResponseBody List<User> getTeamPlayers(@PathVariable("id_team") int id_team) {
        return teamMapper.getTeamPlayers(id_team);
    }

    @RequestMapping(value = "/getlast/{id_player}", method= RequestMethod.GET)
    public @ResponseBody int getLast(@PathVariable("id_player") int id_player) {
        return teamMapper.getLast(id_player);
    }

    @RequestMapping(value = "/create", method={RequestMethod.GET, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertNewTournament(@RequestBody Team json) {

        Team team = new Team(
            -1, json.getName(), json.getLogo(), -1, json.getId_player_1(), -1
        );

        teamMapper.insertNewTeam(team);
    }

    @RequestMapping(value = "/join/{id_team}/{id_player}", method= RequestMethod.PUT)
    public @ResponseBody void joinTeam(@PathVariable("id_team") int id_team, @PathVariable("id_player") int id_player) {
        teamMapper.joinTeam(id_team, id_player);
    }

    @RequestMapping(value = "/delete/{id_team}", method= RequestMethod.DELETE)
    public @ResponseBody void deleteTeam(@PathVariable("id_team") int id_team) {
        teamMapper.deleteTeam(id_team);
    }

    @RequestMapping(value = "/getjoined/{id_player}", method= RequestMethod.GET)
    public @ResponseBody List<Team> getJoined(@PathVariable("id_player") int id_player) {
        return teamMapper.getJoined(id_player);
    }

}
