package us.debloat.studentgradebook.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CliUser {
	@Column(nullable = false, unique = true)
	@NotBlank @NotEmpty @NotNull String name;
	@NotNull
	@Enumerated
	@Column(nullable = false)
	UserTypes userType;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
}
