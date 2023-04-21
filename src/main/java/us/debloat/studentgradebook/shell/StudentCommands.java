package us.debloat.studentgradebook.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import us.debloat.studentgradebook.models.Student;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.service.MainService;
import us.debloat.studentgradebook.service.StudentService;

@ShellComponent
@ShellCommandGroup(value = "students")
public class StudentCommands {
	final MainService mainService;
	final StudentService studentService;

	public StudentCommands(MainService mainService, StudentService studentService) {
		this.mainService = mainService;
		this.studentService = studentService;
	}

	@ShellMethod(key = "grades", value = "Displays student grades.")
	@ShellMethodAvailability("studentCheck")
	public void displayGrades() {
		studentService.getGrades((Student) MainService.user);
	}

	public Availability studentCheck() {
		return MainService.user != null && MainService.user.getUserType() == UserTypes.STUDENT ?
				Availability.available() : Availability.unavailable("Not a Student");
	}
}
