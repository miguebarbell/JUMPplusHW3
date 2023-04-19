package us.debloat.studentgradebook.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends CliUser {
	@OneToMany
	List<Course> classes = new ArrayList<>();
}
