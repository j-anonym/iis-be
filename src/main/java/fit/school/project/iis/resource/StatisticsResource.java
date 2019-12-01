package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.StatisticsMapper;
import fit.school.project.iis.model.StatisticsName;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin(origins = "https://iis-tennis.herokuapp.com")
@RestController
@RequestMapping("/api/statistics")
public class StatisticsResource {

    private StatisticsMapper statisticsMapper;

    public StatisticsResource(StatisticsMapper statisticsMapper) {
        this.statisticsMapper = statisticsMapper;
    }

    @RequestMapping(value = "/getall/players", method = RequestMethod.GET)
    @ResponseBody
    public List<StatisticsName> findAll() {
        return statisticsMapper.findAllPlayers();
    }

    @RequestMapping(value = "/getall/teams", method = RequestMethod.GET)
    @ResponseBody
    public List<StatisticsName> findAllTeams() {
        return statisticsMapper.findAllTeams();
    }
}

