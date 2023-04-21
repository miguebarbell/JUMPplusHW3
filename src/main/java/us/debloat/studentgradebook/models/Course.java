package us.debloat.studentgradebook.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {
	@Column(unique = true)
	String name;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Grade> grades = new ArrayList<>();
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn
	Teacher teacher;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	public void addStudent(Integer studentGrade, Student user) {
		//TODO: create or update

		grades.add(new Grade(user , studentGrade));
	}
	public void deleteStudent(Long studentId) {
		grades = this.grades.stream().filter(grade -> !grade.getStudent().getId().equals(studentId)).toList();
	}
}
