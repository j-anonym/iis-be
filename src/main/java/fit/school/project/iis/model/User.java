package fit.school.project.iis.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class User {
    private Integer id_user;
    private Integer id_stat;
    private String username;
    private String name;
    private String surname;
    private String birth;
    private String sex;
    private String nationality;
    private Boolean is_admin;
    private Boolean is_left_handed;

    public User(Integer id_user, Integer id_stat, String username, String name, String surname, String birth, String sex, String nationality, Boolean is_admin, Boolean is_left_handed) {
        this.id_user = id_user;
        this.id_stat = id_stat;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.sex = sex;
        this.nationality = nationality;
        this.is_admin = is_admin;
        this.is_left_handed = is_left_handed;
    }
}

