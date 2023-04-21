package us.debloat.studentgradebook.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Teacher extends CliUser {
	UserTypes userType = UserTypes.TEACHER;
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Teacher teacher = (Teacher) o;
		return getId() != null && Objects.equals(getId(), teacher.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
