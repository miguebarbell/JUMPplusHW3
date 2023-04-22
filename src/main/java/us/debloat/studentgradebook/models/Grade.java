package us.debloat.studentgradebook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Grade {
	@ManyToOne
	@JoinColumn(name = "student_id")
	private CliUser student;
	private Integer grade;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	public Grade(CliUser student, Integer grade) {
		this.student = student;
		this.grade = grade;
	}

}
