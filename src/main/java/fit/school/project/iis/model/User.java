package fit.school.project.iis.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class User {
    private int id_user;
    private int id_stat;
    private String username;
    private String name;
    private String surname;
    private String birth;
    private String sex;
    private String nationality;
    private boolean is_admin;
    private Boolean is_left_handed;

    public User(int id_user, int id_stat, String username, String name, String surname, String birth, String sex, String nationality, boolean is_admin, Boolean is_left_handed) {
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

