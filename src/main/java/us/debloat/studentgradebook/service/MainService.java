package us.debloat.studentgradebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Service;
import us.debloat.studentgradebook.helper.GradesComparator;
import us.debloat.studentgradebook.helper.Prompt;
import us.debloat.studentgradebook.models.*;
import us.debloat.studentgradebook.repositories.ClassRepository;
import us.debloat.studentgradebook.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MainService {
	public CliUser user = null;
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

	public void listOfClasses() {
		List<Course> all = classRepository.findAll();
		if (all.isEmpty()) {
			Prompt.promptError("No classes registered yet");
		} else {
			Prompt.promptHeader("List of classes");
			String[][] data = new String[all.size() + 1][5];
			data[0][0] = "ID";
			data[0][1] = "Teacher";
			data[0][2] = "Name";
			data[0][3] = "AVG grade";
			data[0][4] = "Students";
			for (int i = 0; i < all.size(); i++) {
				Course classById = classRepository.findById(all.get(i).getId()).get();
				Teacher teacher = all.get(i).getTeacher();
				data[i + 1][0] = all.get(i).getId().toString();
				data[i + 1][1] = null == teacher ? "Not Assigned" : teacher.getName();
				data[i + 1][2] = all.get(i).getName();
				data[i + 1][3] = (classById.getGrades().size() == 0) ? "No grades" :
						String.valueOf(classById.getGrades().stream()
						                        .mapToInt(Grade::getGrade)
						                        .boxed()
						                        .reduce(Integer::sum).get()
						               / classById.getGrades().size());
				data[i + 1][4] = String.valueOf(classById.getGrades().size());
			}
			printTable(data);
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

	public Course createClass(String newClass) {
		Course course = new Course();
		course.setName(newClass);
		return classRepository.save(course);
	}

	public void assignClassToTeacher(Long classId) {
		Optional<Course> classById = classRepository.findById(classId);
		if (classById.isPresent()) {
			classById.get().setTeacher((Teacher) user);
			classRepository.save(classById.get());
		} else {
			Prompt.promptError("Class not found");
		}
	}

	public void getClassDetails(Long classId) {
		Optional<Course> classById = classRepository.findById(classId);
		if (classById.isPresent()) {
			List<Grade> grades = classById.get().getGrades()
			                              .stream()
			                              .sorted(new GradesComparator())
			                              .toList();
			int[] arrayGrades = grades.stream().mapToInt(Grade::getGrade).toArray();
			double average = Arrays.stream(arrayGrades).average().getAsDouble();
			int median = arrayGrades.length % 2 != 0 ?
					arrayGrades[(int) Math.ceil(arrayGrades.length / 2)] :
					(arrayGrades[(arrayGrades.length / 2) - 1] +
					arrayGrades[(arrayGrades.length / 2)])/2;
			Prompt.promptHeader("AVG grade = " + average);
			Prompt.promptHeader("Median grade = " + median);

			if (grades.size() > 0) {
				String[][] data = new String[grades.size() + 1][2];
				data[0][0] = "Grade";
				data[0][1] = "Name";
				AtomicInteger index = new AtomicInteger(1);
				grades.forEach(grade -> {
					data[index.get()][0] = grade.getGrade().toString();
					data[index.getAndIncrement()][1] = grade.getStudent().getName();
				});
				printTable(data);
			} else {
				Prompt.promptError("No students in this class.");
			}
		} else {
			Prompt.promptError("Class not found");
		}
	}

	public void listAllStudents() {
		List<CliUser> byUserType = userRepository.findByUserType(UserTypes.STUDENT);
		if (byUserType.isEmpty()) {
			Prompt.promptError("No Students registered");
		} else {
			Prompt.promptFeedback("List of Students");
			String[][] data = new String[byUserType.size() + 1][2];
			data[0][0] = "ID";
			data[0][1] = "Name";
			for (int i = 0; i < byUserType.size(); i++) {
				CliUser student = byUserType.get(i);
				data[i + 1][0] = student.getId().toString();
				data[i + 1][1] = student.getName();
			}
			printTable(data);
		}
	}

	private void printTable(String[][] data) {
		TableModel tableModel = new ArrayTableModel(data);
		TableBuilder tableBuilder = new TableBuilder(tableModel);
		System.out.println(tableBuilder
				.addFullBorder(BorderStyle.air)
				.addHeaderBorder(BorderStyle.oldschool)
				.build().render(100));
	}

	public void addStudent(Long studentId, Integer studentGrade, Long courseId) {
		Optional<CliUser> studentById = userRepository.findById(studentId);
		studentById.ifPresent(user ->
				classRepository.findById(courseId)
				               .ifPresent(course -> {
					               course.addStudent(studentGrade, (Student) user);
					               classRepository.save(course);
				               }));
	}
}
