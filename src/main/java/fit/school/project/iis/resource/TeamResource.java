package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.TeamMapper;
import fit.school.project.iis.model.Team;
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

    @RequestMapping(value = "/getlast/{id_player_1}", method= RequestMethod.GET)
    public @ResponseBody Team getLast(@PathVariable("id_player_1") int id_player_1) {
        return teamMapper.getLast(id_player_1);
    }

    @RequestMapping(value = "/create", method={RequestMethod.GET, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertNewTournament(@RequestBody Team json) {

        Team team = new Team(
            -1, json.getName(), json.getLogo(), -1, json.getId_player_1(), -1
        );

        teamMapper.insertNewTeam(team);
    }

}
