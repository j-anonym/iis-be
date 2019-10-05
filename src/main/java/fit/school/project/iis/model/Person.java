package fit.school.project.iis.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Person {
    private int personid;
    private String lastname;
    private String firstname;
    private String address;
    private String city;
}
