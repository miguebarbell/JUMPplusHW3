package us.debloat.studentgradebook.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import us.debloat.studentgradebook.models.Course;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.service.MainService;
import us.debloat.studentgradebook.service.TeacherService;

@ShellComponent
@ShellCommandGroup(value = "Teacher Commands")
public class TeacherCommands {
	@Autowired
	MainService mainService;
	@Autowired
	TeacherService teacherService;

	// FIXME: better name for this method, is related to add a grade to an student.
	@ShellMethod(value = "Add students to a course, and manage their grades.")
	@ShellMethodAvailability("teacherCheck")
	public void addStudent(
			@ShellOption(value = {"-s", "--student"},
					arity = 1,
					help = "This is the student ID") Long studentId,
			@ShellOption(value = {"-g", "--grad"},
					arity = 1,
					help = "Student grade") Integer studentGrade,
			@ShellOption(value = {"-c", "--course"},
					arity = 1,
					help = "Course Id") Long courseId
	) {
		teacherService.addStudent(studentId, studentGrade, courseId);
	}
	// TODO: create method to remove an student

	@ShellMethod(key = "students", value = "Get all the students")
	@ShellMethodAvailability("teacherCheck")
	public void students() {
		teacherService.listAllStudents();
	}


	@ShellMethod(key = "courses", value = "List of courses")
	@ShellMethodAvailability("teacherCheck")
	public void listOfClasses() {
		teacherService.listOfClasses();
	}

	@ShellMethod(key = "class", value = "Create, assign a class")
	@ShellMethodAvailability("teacherCheck")
	public void getClass(
			@ShellOption(value = {"-c", "--create"},
					arity = 3,
					defaultValue = ShellOption.NULL,
					help = "Create a new class.") String newClass,
			@ShellOption(value = {"-l", "--list"},
					arity = 1,
					defaultValue = ShellOption.NULL,
					help = "List the students and their grades for a specific course") Long getClass,
			@ShellOption(value = {"-a", "--assign"}, arity = 1, defaultValue = ShellOption.NULL,
					help = "Assign this class to you.") Long assignClass) {
		if (null != newClass) {
			Course course = teacherService.createClass(newClass);
		} else if (null != assignClass) {
			teacherService.assignClassToTeacher(assignClass);
		} else {
			teacherService.getClassDetails(getClass);
		}
	}

	public Availability teacherCheck() {
		return mainService.user != null && mainService.user.getUserType() == UserTypes.TEACHER ?
				Availability.available() : Availability.unavailable("Not a Teacher");
	}
}
