package us.debloat.studentgradebook.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CliUser {
	UserTypes userType;
	@Column(unique = true)
	String name;
	@Id
	@Column(updatable = false, nullable = false, name = "id", unique = true)
	String id;
	String password;
}
