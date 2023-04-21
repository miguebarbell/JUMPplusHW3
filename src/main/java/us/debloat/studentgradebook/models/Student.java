package us.debloat.studentgradebook.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Student extends CliUser {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@Column(name = "id", nullable = false, updatable = false)
//	private String id;
	private UserTypes userType = UserTypes.STUDENT;
}
