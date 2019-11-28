package fit.school.project.iis.model;
import java.util.Date;

public class AccountTransfer {
	private String username;
	private String password;
	private String name;
	private String surname;
	private String nationality;
	private Date birthdate;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
        return birthdate;
	}

	public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
	}
	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}