package us.debloat.studentgradebook.helper;

import org.junit.jupiter.api.Assertions;

import java.util.stream.Stream;

class ValidatorsTest {

	@org.junit.jupiter.api.Test
	void validateEmail() {
		// good examples
		Stream.of(
				    "miguel@example.org",
				    "some.thin@cognixia.cc"
		    )
		      .map(Validators::validateEmail)
		      .forEach(Assertions::assertTrue);

		// bad examples
		Stream.of(
				"migue.badexample.com",
				"some.thin@cognixia",
				"@cognixia.com.cc"
				)
				.map(Validators::validateEmail)
				.forEach(Assertions::assertFalse);


	}
}
