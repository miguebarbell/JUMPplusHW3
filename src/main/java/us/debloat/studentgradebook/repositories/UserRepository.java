package us.debloat.studentgradebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import us.debloat.studentgradebook.models.CliUser;
import us.debloat.studentgradebook.models.UserTypes;

import java.util.List;

public interface UserRepository extends JpaRepository<CliUser, String> {

//	@Query("select c from CliUser c where c.userType = ?1")
@Query("select c from CliUser c where c.userType = ?1")
List<CliUser> findByUserType(UserTypes type);
}
