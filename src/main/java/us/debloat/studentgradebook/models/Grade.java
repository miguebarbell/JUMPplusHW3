package us.debloat.studentgradebook.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Grade {
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	private Integer grade;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	public Grade() {

	}

	public Grade(Student student, Integer grade) {
		this.student = student;
		this.grade = grade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
