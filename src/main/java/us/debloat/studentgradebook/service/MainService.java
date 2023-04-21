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

import java.util.List;
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

	public void login(String userId) {
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
				userRepository.save(newStudent);

				Prompt.promptHeader(newStudent.getUserType() + ": "+newStudent.getName()+"\nID: " + newStudent.getId());
			}
		}
	}

}
