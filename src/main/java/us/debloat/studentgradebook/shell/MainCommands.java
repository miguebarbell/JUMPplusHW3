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
			@ShellOption(value = {"--teacher"},
					arity = 2,
					defaultValue = "") String teacher,
			@ShellOption(
					value = {"--student"},
					arity = 2,
					defaultValue = "") String student) {
		mainService.register(teacher, student);
	}

	@ShellMethod(key = "login", value = "Login as student or teacher")
	@ShellMethodAvailability("loginCheck")
	public void login(@ShellOption(arity = 1) String userId) {
		mainService.login(userId);
	}

	@ShellMethod(key = "logout", value = "self explanatory")
	@ShellMethodAvailability("logoutCheck")
	public void logout() {
		mainService.logout();
	}

	@Override
	public AttributedString getPrompt() {
		return mainService.user == null ?
				new AttributedString(prompt + " >",
						AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)) :
				new AttributedString(mainService.user.getName() + " >",
						AttributedStyle.INVERSE.foreground(AttributedStyle.GREEN));
	}


	public Availability adminCheck() {
		return mainService.user != null && mainService.user.getUserType() == UserTypes.ADMIN ?
				Availability.available() : Availability.unavailable("Not an Admin");
	}

	public Availability logoutCheck() {
		return mainService.user != null ?
				Availability.available() : Availability.unavailable("Not logged in");
	}

	public Availability loginCheck() {
		return mainService.user == null ?
				Availability.available() : Availability.unavailable("Already logged in");
	}
}
