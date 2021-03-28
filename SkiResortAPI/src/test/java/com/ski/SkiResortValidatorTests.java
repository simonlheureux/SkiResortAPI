package com.ski;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkiResortValidatorTests {

	@Test
	void whenSkiResortNameEmpty_thenThrowException() {

		SkiResort skiResort = new SkiResort(1, "", "Description");

		Exception exception = assertThrows(ValidationException.class, () -> {
			SkiResortValidator.validateSkiResort(skiResort);
		});

		String expectedMessage = "Ski resort name must not be empty.";
		String message = exception.getMessage();

		assertTrue(message.equals(expectedMessage));
	}

	@Test
	void whenSkiResortNameNull_thenThrowException() {

		SkiResort skiResort = new SkiResort(1, null, "Description");

		Exception exception = assertThrows(ValidationException.class, () -> {
			SkiResortValidator.validateSkiResort(skiResort);
		});

		String expectedMessage = "Ski resort name must not be empty.";
		String message = exception.getMessage();

		assertTrue(message.equals(expectedMessage));
	}

	@Test
	void whenSkiResortNameTooLong_thenThrowException() {

		SkiResort skiResort = new SkiResort(1, "123456789101234567891012345678910123456789101234567891012345678910",
				"Description");

		Exception exception = assertThrows(ValidationException.class, () -> {
			SkiResortValidator.validateSkiResort(skiResort);
		});

		String expectedMessage = "Ski resort name must have less than 50 characters.";
		String message = exception.getMessage();

		assertTrue(message.equals(expectedMessage));
	}

}
