package fit.school.project.iis.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class User {
    private int id_user;
    private int id_stat;
    private String name;
    private String surname;
    private String birth;
    private String sex;
    private String nationality;
    private String type;
    private boolean is_left_handed;
}
