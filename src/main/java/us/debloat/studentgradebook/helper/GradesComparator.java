package us.debloat.studentgradebook.helper;


import us.debloat.studentgradebook.models.Grade;

import java.util.Comparator;
import java.util.Objects;

public class GradesComparator implements Comparator<Grade> {
	@Override
	public int compare(Grade o1, Grade o2) {
		if (Objects.equals(o1.getGrade(), o2.getGrade())) {
			return o1.getStudent().getName().compareTo(o2.getStudent().getName());
		} else {
			return - o1.getGrade().compareTo(o2.getGrade());
		}
	}
}
