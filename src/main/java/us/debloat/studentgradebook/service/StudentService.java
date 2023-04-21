package us.debloat.studentgradebook.service;

import org.springframework.stereotype.Service;
import us.debloat.studentgradebook.helper.MenuParser;
import us.debloat.studentgradebook.helper.Prompt;
import us.debloat.studentgradebook.models.Course;
import us.debloat.studentgradebook.models.Student;
import us.debloat.studentgradebook.repositories.ClassRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentService {
	final ClassRepository classRepository;

	public StudentService(ClassRepository classRepository) {
		this.classRepository = classRepository;
	}

	public void getGrades(Student student) {
		List<Course> byGradesStudent = classRepository.findByGrades_Student((Student) student);
		List<List<String>> listData = new ArrayList<>();
		listData.add(new ArrayList<>());
		listData.get(0).add("Course");
		listData.get(0).add("Grades");
		AtomicInteger indexer = new AtomicInteger(1);
		byGradesStudent.forEach(course -> {
			String name = course.getName();
			course.getGrades().stream()
			      .filter(grades -> grades.getStudent().getId().equals(student.getId()))
					.forEach(courseGrades -> {
						listData.add(new ArrayList<>());
						listData.get(indexer.get()).add(name);
						listData.get(indexer.getAndIncrement()).add(String.valueOf(courseGrades.getGrade()));
					});
		});
		Prompt.promptHeader("Your grades %s".formatted(student.getName()));
		String[][] data = new String[listData.size()][2];
		for (int i = 0; i < listData.size(); i++) {
			for (int j = 0; j < listData.get(i).size(); j++) {
				data[i][j] = listData.get(i).get(j);
			}
		}
		MenuParser.printTable(data);
	}
}
