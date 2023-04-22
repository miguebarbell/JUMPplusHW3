package us.debloat.studentgradebook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends CliUser {
	private UserTypes userType = UserTypes.STUDENT;
}
