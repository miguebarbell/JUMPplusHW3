package us.debloat.studentgradebook.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends CliUser {
	@ManyToMany(cascade = CascadeType.ALL)
	List<Course> classes = new ArrayList<>();
}
