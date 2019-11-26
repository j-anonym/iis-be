package fit.school.project.iis.resource;

import fit.school.project.iis.mapper.TournamentMapper;
import fit.school.project.iis.model.Tournament;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/tournament")
public class TournamentResource {

    private TournamentMapper tournamentMapper;

    public TournamentResource(TournamentMapper tournamentMapper){
        this.tournamentMapper = tournamentMapper;
    }

    @RequestMapping(value = "/create", method={RequestMethod.GET, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertNewTournament(@RequestBody Tournament json) throws ParseException {

//        Date date_from = new SimpleDateFormat("dd/MM/yyyy").parse(json.getDate_from());
//        Date date_to = new SimpleDateFormat("dd/MM/yyyy").parse(json.getDate_to());
        System.out.println(json.getType());

        Tournament tournament = new Tournament(
                json.getPrize(), json.getName(), json.getDate_from(), json.getDate_to(), json.getPlace(), json.getOccupation(),
                json.getCost(), json.getCapacity(), json.is_singles(), json.getType(), json.getSponsors(), json.getId_staff()
        );

        tournamentMapper.insertNewTournament(tournament);
    }
}
