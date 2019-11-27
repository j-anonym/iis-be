package fit.school.project.iis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fit.school.project.iis.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
	
	Account findByUsername(String username);
	
}
