package fit.school.project.iis;

import fit.school.project.iis.mapper.PersonMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("fit.school.project.iis.mapper")
public class IisApplication extends SpringBootServletInitializer {

	private final PersonMapper personMapper;

	public IisApplication(PersonMapper personMapper){
		this.personMapper = personMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(IisApplication.class, args);
	}

}
