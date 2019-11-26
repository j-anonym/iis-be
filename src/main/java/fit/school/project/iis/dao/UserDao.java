package fit.school.project.iis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fit.school.project.iis.model.DAOUser;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
}
