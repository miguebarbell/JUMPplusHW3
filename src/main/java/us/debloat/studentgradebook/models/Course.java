package us.debloat.studentgradebook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Course {
	@Column(unique = true)
	String name;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Grade> grades = new ArrayList<>();
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn
	CliUser teacher;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	public void addStudentGrade(Integer studentGrade, CliUser user) {
		grades.add(new Grade(user , studentGrade));
	}
	public void deleteStudent(String studentId) {
		grades = this.grades.stream().filter(grade -> !grade.getStudent().getId().equals(studentId)).toList();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Course course = (Course) o;
		return getId() != null && Objects.equals(getId(), course.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
