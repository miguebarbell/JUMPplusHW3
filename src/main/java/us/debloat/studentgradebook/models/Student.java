package us.debloat.studentgradebook.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends CliUser {
	@ManyToMany
	List<Course> classes = new ArrayList<>();
}
