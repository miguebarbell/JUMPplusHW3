package us.debloat.studentgradebook.helper;

public class Prompt {
	private static void commonPrompt(String message, String initialColor, String finalColor) {
		System.out.printf("%s" + message + "\n%s", initialColor, finalColor);
	}

	public static void prompt(String message) {
		commonPrompt(message, Colors.RESET, Colors.YELLOW);
	}

	public static void promptFeedback(String message) {
		commonPrompt(message, Colors.GREEN, Colors.RESET);
	}

	public static void promptOptions(String message) {
		commonPrompt(message, Colors.GREEN, Colors.RESET);
	}

	public static void promptHeader(String message) {
		commonPrompt(message, Colors.BLUE, Colors.RESET);
	}

	public static void promptError(String message) {
		commonPrompt(message, Colors.RED, Colors.RESET);
	}
}
