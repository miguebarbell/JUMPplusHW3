package us.debloat.studentgradebook.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.service.MainService;

@ShellComponent
@ShellCommandGroup(value = "students")
public class StudentCommands {
	@Autowired
	MainService mainService;

//	@ShellMethod(key = "login", value = "self explanatory")
//	@ShellMethodAvailability("loginCheck")
//	public void login() {
//		mainService.login();
//	}

	public Availability studentCheck() {
		return mainService.user != null && mainService.user.getUserType() == UserTypes.STUDENT ?
				Availability.available() : Availability.unavailable("Not a Student");
	}
}
