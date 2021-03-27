package com.ski;

public class SkiResortValidator {

	public static void validateSkiResort(SkiResort skiResort) throws ValidationException {

		if (skiResort.getName() == null || skiResort.getName().isBlank()) {
			throw new ValidationException("Ski resort name must not be empty.");
		}

		if (skiResort.getName().length() > 50) {
			throw new ValidationException("Ski resort name must have less than 50 characters.");
		}
	}

}
