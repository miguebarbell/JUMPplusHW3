package us.debloat.studentgradebook.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.service.MainService;
import us.debloat.studentgradebook.service.StudentService;

@ShellComponent
@AllArgsConstructor
@ShellCommandGroup(value = "students")
public class StudentCommands {
	final private StudentService studentService;

	@ShellMethod(key = "grades", value = "Displays student grades.")
	@ShellMethodAvailability("studentCheck")
	public void displayGrades() {
		studentService.getGrades(MainService.user);
	}

	public Availability studentCheck() {
		return MainService.user != null && MainService.user.getUserType() == UserTypes.STUDENT ?
				Availability.available() : Availability.unavailable("Not a Student");
	}
}
