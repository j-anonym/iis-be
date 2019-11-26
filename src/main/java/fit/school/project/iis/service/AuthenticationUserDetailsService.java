package fit.school.project.iis.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import fit.school.project.iis.model.DAOUser;
import fit.school.project.iis.dao.UserDao;
import fit.school.project.iis.model.UserDTO;

@Service 
public class AuthenticationUserDetailsService implements UserDetailsService {

    @Autowired
	private UserDao userDao;

	@Autowired
    private PasswordEncoder bcryptEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("test".equals(username)) {
			return new User("test", "$2a$10$jqKo8Cd0xxd.XP1EXMz9v.WglmK0ukirT8ajNSN1D9Ztu2vnBUZa.",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
    }
    
    public DAOUser save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}
}
