package us.debloat.studentgradebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.debloat.studentgradebook.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
