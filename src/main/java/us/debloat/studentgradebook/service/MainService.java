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

@Service
@AllArgsConstructor
public class MainService {
	public static CliUser user = null;
	private final UserRepository userRepository;
//	private final ClassRepository classRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public void logout() {
		Prompt.promptHeader("Bye %s!".formatted(user.getName()));
		SecurityContextHolder.clearContext();
		user = null;
	}

	public void login(String userId, String password) {
//		Optional<CliUser> byId = userRepository.findById(userId);
//		if (byId.isPresent()) {
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
//		else {
//		}
//	}



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
			if (!teacher.equals("")) {
				CliUser newTeacher = new CliUser();
				newTeacher.setName(teacher);
				List<CliUser> teachers = userRepository.findByUserType(UserTypes.TEACHER);
				if (teachers.size() > 0) {
					Integer max = Integer.parseInt(teachers.get(teachers.size() - 1).getId().replace(teacherPrefix, ""));
					newTeacher.setId(max++ > 9 ? teacherPrefix + max : teacherPrefix + "0" + max);
				} else {
					newTeacher.setId(teacherPrefix + "01");
				}
				newTeacher.setUserType(UserTypes.TEACHER);
				newTeacher.setPassword(passwordEncoder.encode(password));
				userRepository.save(newTeacher);
				Prompt.promptHeader(newTeacher.getUserType() + ": "+newTeacher.getName()+"\nID: " + newTeacher.getId());
			} else {
				CliUser newStudent = new CliUser();
				newStudent.setName(student);
				List<CliUser> students = userRepository.findByUserType(UserTypes.STUDENT);
				if (students.size() > 0) {
					Integer max = Integer.parseInt(students.get(students.size() - 1).getId().replace(studentPrefix, ""));
					newStudent.setId(max++ > 9 ? studentPrefix + max : studentPrefix + "0" + max);
				} else {
					newStudent.setId(studentPrefix + "01");
				}
				newStudent.setUserType(UserTypes.STUDENT);
				newStudent.setPassword(passwordEncoder.encode(password));
				userRepository.save(newStudent);
				Prompt.promptHeader(newStudent.getUserType() + ": "+newStudent.getName()+"\nID: " + newStudent.getId());
			}
		}
	}

}
