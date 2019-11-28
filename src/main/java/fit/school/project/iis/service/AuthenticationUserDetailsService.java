package fit.school.project.iis.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fit.school.project.iis.repository.AccountRepository;
import fit.school.project.iis.model.Account;
import fit.school.project.iis.model.AccountTransfer;


@Service
public class AuthenticationUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),
				new ArrayList<>());

		//if ("test".equals(username)) {
			//return new User("test", "$2a$10$cnVZO74FBXprOVG/Pm3UsOwosdZijOEMzAtv0itI1C2oLBhtMW5f.",
					//new ArrayList<>());
		//} else {
			//throw new UsernameNotFoundException("User not found with username: " + username);
		//}
		
	}

	public Account save(AccountTransfer account) {
		Account newAccount = new Account();
		newAccount.setUsername(account.getUsername());
		newAccount.setPassword(bcryptEncoder.encode(account.getPassword()));
		newAccount.setName(account.getName());
		newAccount.setSurname(account.getSurname());
		newAccount.setNationality(account.getNationality());
		newAccount.setBirthdate(account.getBirthdate());
		return accountRepository.save(newAccount);
	}

}