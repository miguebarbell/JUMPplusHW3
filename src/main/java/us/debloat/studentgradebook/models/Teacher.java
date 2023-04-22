package us.debloat.studentgradebook.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Teacher extends CliUser {
	UserTypes userType = UserTypes.TEACHER;
}
