package us.debloat.studentgradebook.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import us.debloat.studentgradebook.helper.Prompt;
import us.debloat.studentgradebook.models.CliUser;
import us.debloat.studentgradebook.models.SecurityUser;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.repositories.UserRepository;

import java.util.List;

import static us.debloat.studentgradebook.models.UserTypes.*;
import static us.debloat.studentgradebook.models.UserTypes.STUDENT;

@Service
@AllArgsConstructor
public class MainService {
	public static CliUser user = null;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public void logout() {
		Prompt.promptHeader("Bye %s!".formatted(user.getName()));
		SecurityContextHolder.clearContext();
		user = null;
	}

	public void login(String userId, String password) {
		Authentication request = new UsernamePasswordAuthenticationToken(userId, password);
		try {
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = principal.getUser();
			Prompt.promptFeedback("Welcome " + user.getName() + ", you are our best " + user.getUserType());
		} catch (AuthenticationException e) {
			Prompt.promptError("Bad Credentials");
		}
	}

	private String nextIdGenerator(UserTypes type, String prefix) {
		List<CliUser> users = userRepository.findByUserType(type);
		if (!users.isEmpty()) {
			int max = Integer.parseInt(users.get(users.size() - 1).getId().replace(prefix, ""));
			return max++ > 9 ? prefix + max : prefix + "0" + max;
		} else {
			return prefix + "01";
		}
	}

	public void register(String teacher, String student, String password) {
		if (student == null || teacher == null) {
			Prompt.promptError("Please provide a name");
		} else if (student.equals("") && teacher.equals("")) {
			Prompt.promptError("should select only one option and a valid argument for that.");
		} else if (!teacher.equals("") && !student.equals("")) {
			Prompt.promptError("Both options selected, please select only one");
		} else {
			String teacherPrefix = "t+";
			String studentPrefix = "s+";
			boolean isTeacher = !teacher.equals("");
			CliUser newUser = new CliUser();
			newUser.setName(isTeacher ? teacher : student);
			newUser.setId(nextIdGenerator(isTeacher ? TEACHER : STUDENT,
					isTeacher ? teacherPrefix : studentPrefix));
			newUser.setUserType(isTeacher ? TEACHER : STUDENT);
			newUser.setPassword(passwordEncoder.encode(password));
			try {
				userRepository.save(newUser);
				Prompt.promptHeader(newUser.getUserType() + ": " + newUser.getName() + "\nID: " + newUser.getId());
			} catch (RuntimeException e) {
				Prompt.promptError("Cannot save user, name already in use");
			}
		}
	}

}
