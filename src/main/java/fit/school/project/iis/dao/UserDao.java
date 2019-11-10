package fit.school.project.iis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.DAOUser;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
}
