package us.debloat.studentgradebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.debloat.studentgradebook.helper.Prompt;
import us.debloat.studentgradebook.models.CliUser;
import us.debloat.studentgradebook.models.Student;
import us.debloat.studentgradebook.models.Teacher;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.repositories.ClassRepository;
import us.debloat.studentgradebook.repositories.UserRepository;

import java.util.Optional;

@Service
public class MainService {
	public static CliUser user = null;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ClassRepository classRepository;

	public void logout() {
		Prompt.promptHeader("Bye %s!".formatted(user.getName()));
		user = null;
	}

	public void login(Long userId) {
		Optional<CliUser> byId = userRepository.findById(userId);
		if (byId.isPresent()) {
			user = byId.get();
			Prompt.promptFeedback("Welcome " + byId.get().getName() + ", you are our best " + user.getUserType());
		} else {
			Prompt.promptError("Could not find user");
		}
	}



	public void register(String teacher, String student) {
		if (student == null || teacher == null) {
			Prompt.promptError("Please provide a name");
		} else if (student.equals("") && teacher.equals("")) {
			Prompt.promptError("should select only one option and a valid argument for that.");
		} else if (!teacher.equals("") && !student.equals("")) {
			Prompt.promptError("Both options selected, please select only one");
		} else {
			if (!teacher.equals("")) {
				Teacher newTeacher = new Teacher();
				newTeacher.setName(teacher);
				newTeacher.setUserType(UserTypes.TEACHER);
				userRepository.save(newTeacher);
			} else {
				Student newStudent = new Student();
				newStudent.setName(student);
				newStudent.setUserType(UserTypes.STUDENT);
				userRepository.save(newStudent);
			}
		}
	}

}
