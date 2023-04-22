package us.debloat.studentgradebook.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import us.debloat.studentgradebook.helper.Prompt;
import us.debloat.studentgradebook.models.Course;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.service.MainService;
import us.debloat.studentgradebook.service.TeacherService;

@ShellComponent
@AllArgsConstructor
@ShellCommandGroup(value = "Teacher Commands")
public class TeacherCommands {
	private final TeacherService teacherService;

	@ShellMethod(key="student", value = "Add students to a course, and manage their grades.")
	@ShellMethodAvailability("teacherCheck")
	public void addStudent(
			@ShellOption(
					arity = 1,
					help = "This is the student ID") String studentId,
			@ShellOption(value = {"-g", "--grad"},
					arity = 1,
					help = "Student grade") Integer studentGrade,
			@ShellOption(value = {"-c", "--course"},
					arity = 1,
					help = "Course Id") Long courseId
	) {
		teacherService.addStudent(studentId, studentGrade, courseId);
	}

	@ShellMethod(value = "Remove a student.")
	@ShellMethodAvailability("teacherCheck")
	public void removeStudent(String studentId) {
		teacherService.removeStudent(studentId);

	}

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
			Prompt.promptFeedback("Class created \nClass: " + course.getName() + "\nID: " + course.getId());
		} else if (null != assignClass) {
			teacherService.assignClassToTeacher(assignClass);
		} else {
			teacherService.getClassDetails(getClass);
		}
	}

	public Availability teacherCheck() {
		return MainService.user != null && MainService.user.getUserType() == UserTypes.TEACHER ?
				Availability.available() : Availability.unavailable("Not a Teacher");
	}
}
