package us.debloat.studentgradebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import us.debloat.studentgradebook.models.CliUser;
import us.debloat.studentgradebook.models.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
	@Transactional
	@Modifying
	@Query("delete from Grade g where g.student = ?1")
	void deleteByStudent(CliUser student);
}
