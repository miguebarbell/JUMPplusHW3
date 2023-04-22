package us.debloat.studentgradebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import us.debloat.studentgradebook.models.CliUser;
import us.debloat.studentgradebook.models.Course;
import us.debloat.studentgradebook.models.Student;

import java.util.List;

public interface ClassRepository extends JpaRepository<Course, Long> {
	@Query("select c from Course c inner join c.grades grades where grades.student = ?1")
	List<Course> findByGrades_Student(CliUser student);
}
