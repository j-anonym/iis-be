package fit.school.project.iis.model;

import lombok.Data;

@Data
public class Tournament {
    private int prize;
    private String name;
    private String date_from;
    private String date_to;
    private String place;
    private int occupation;
    private int cost;
    private int capacity;
    private boolean is_singles;
    private String type;
    private String sponsors;
    private int id_staff;

    // constructor
    public Tournament (int prize, String name, String date_from, String date_to, String place,
                       int occupation, int cost, int capacity, boolean is_singles, String type,
                       String sponsors, int id_staff) {
        this.prize = prize;
        this.name = name;
        this.date_from = date_from;
        this.date_to = date_to;
        this.place = place;
        this.occupation = occupation;
        this.cost = cost;
        this.capacity = capacity;
        this.is_singles = is_singles;
        this.type = type;
        this.sponsors = sponsors;
        this.id_staff = id_staff;
    }
}


