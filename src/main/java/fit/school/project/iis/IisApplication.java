package fit.school.project.iis;

import fit.school.project.iis.mapper.TournamentMapper;
import fit.school.project.iis.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("fit.school.project.iis.mapper")
public class IisApplication extends SpringBootServletInitializer {

	private final UserMapper userMapper;
	private final TournamentMapper tournamentMapper;

	public IisApplication(UserMapper userMapper, TournamentMapper tournamentMapper){
		this.userMapper = userMapper;
		this.tournamentMapper = tournamentMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(IisApplication.class, args);
	}

}
