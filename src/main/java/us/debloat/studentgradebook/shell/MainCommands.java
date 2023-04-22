package us.debloat.studentgradebook.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import us.debloat.studentgradebook.models.UserTypes;
import us.debloat.studentgradebook.service.MainService;
@ShellComponent
public class MainCommands implements PromptProvider {
	@Autowired
	MainService mainService;
	String prompt = "";

	@ShellMethod(key = "register", value = "Register a new Student or Teacher")
	public void register(
			@ShellOption(value = {"--teacher", "-t"},
					arity = 2,
					defaultValue = "") String teacher,
			@ShellOption(
					value = {"--student", "-s"},
					arity = 2,
					defaultValue = "") String student,
			@ShellOption(
					value = {"-p", "--password"},
					arity = 1
			) String password) {
		mainService.register(teacher, student, password);
	}

	@ShellMethod(key = "login", value = "Login as student or teacher")
	@ShellMethodAvailability("loginCheck")
	public void login(
			@ShellOption(arity = 1) String userId,
			@ShellOption(arity = 1) String password) {
		mainService.login(userId, password);
	}

	@ShellMethod(key = "logout", value = "self explanatory")
	@ShellMethodAvailability("logoutCheck")
	public void logout() {
		mainService.logout();
	}

	@Override
	public AttributedString getPrompt() {
		return MainService.user == null ?
				new AttributedString(prompt + " >",
						AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)) :
				new AttributedString(MainService.user.getName() + " >",
						AttributedStyle.INVERSE.foreground(AttributedStyle.GREEN));
	}


	public Availability adminCheck() {
		return MainService.user != null && MainService.user.getUserType() == UserTypes.ADMIN ?
				Availability.available() : Availability.unavailable("Not an Admin");
	}

	public Availability logoutCheck() {
		return MainService.user != null ?
				Availability.available() : Availability.unavailable("Not logged in");
	}

	public Availability loginCheck() {
		return MainService.user == null ?
				Availability.available() : Availability.unavailable("Already logged in");
	}
}
