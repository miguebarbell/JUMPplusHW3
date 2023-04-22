package us.debloat.studentgradebook.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import us.debloat.studentgradebook.helper.Prompt;
import us.debloat.studentgradebook.models.CliUser;
import us.debloat.studentgradebook.models.SecurityUser;
import us.debloat.studentgradebook.repositories.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class DetailsService implements UserDetailsService {

	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<CliUser> byId = userRepository.findById(username);
		if (byId.isPresent()) {
			return new SecurityUser(byId.get());
		} else {
			Prompt.promptError("User not found");
			return null;
		}
	}
}
