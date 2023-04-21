package us.debloat.studentgradebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.debloat.studentgradebook.models.Course;
import us.debloat.studentgradebook.models.Grade;
import us.debloat.studentgradebook.models.Student;

import java.util.List;

public interface ClassRepository extends JpaRepository<Course, Long> {
	List<Course> findByGrades_Student(Student student);
	long deleteByGrades(Grade grades);

}
