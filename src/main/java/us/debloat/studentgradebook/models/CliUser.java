package us.debloat.studentgradebook.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CliUser {
	private UserTypes userType;
	@Column(unique = true)
	private String name;
	@Id
	@Column(updatable = false, nullable = false, name = "id", unique = true)
	private String id;
	private String password;
}
