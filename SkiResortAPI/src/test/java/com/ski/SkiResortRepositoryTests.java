package com.ski;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkiResortRepositoryTests {

	@Autowired
	private SkiResortRepository skiResortRepository;

	@Test
	void testGetAllSkiResorts() {

		List<SkiResort> skiResorts = skiResortRepository.getAllSkiResorts();

		assertNotNull(skiResorts);
		assertTrue(skiResorts.size() == 3);
	}

	@Test
	void testGetSkiResort() throws ItemNotFoundException {

		SkiResort skiResort = skiResortRepository.getSkiResort(1);

		assertNotNull(skiResort);
		assertTrue(skiResort.getId() == 1);
	}

	@Test
	void whenSkiResortDoesntExist_thenThrowException() {

		Exception exception = assertThrows(ItemNotFoundException.class, () -> {
			skiResortRepository.getSkiResort(999);
		});

		String expectedMessage = "Ski resort not found for id: 999";
		String message = exception.getMessage();

		assertTrue(expectedMessage.equals(message));
	}

	@Test
	void testAddSkiResort() throws ItemNotFoundException {

		SkiResort newSkiResort = new SkiResort(-1, "testAddSkiResort", "Description");
		SkiResort addedSkiResort = skiResortRepository.addSkiResort(newSkiResort);

		assertNotNull(addedSkiResort);
		assertTrue(addedSkiResort.getId() != -1);
		assertNotNull(skiResortRepository.getSkiResort(addedSkiResort.getId()));
	}

	@Test
	void testUpdateSkiResort() throws ItemNotFoundException {

		SkiResort updatedSkiResort = new SkiResort(1, "Name1Update", "Description");
		SkiResort addedSkiResort = skiResortRepository.updateSkiResort(1, updatedSkiResort);

		assertNotNull(addedSkiResort);
		assertTrue(addedSkiResort.getId() == 1);
		assertTrue("Name1Update".equals(skiResortRepository.getSkiResort(addedSkiResort.getId()).getName()));
	}

	@Test
	void testDeleteSkiResort() throws ItemNotFoundException {

		int skiResortsCount = skiResortRepository.getAllSkiResorts().size();
		skiResortRepository.deleteSkiResort(1);

		assertTrue(skiResortRepository.getAllSkiResorts().size() < skiResortsCount);
		assertThrows(ItemNotFoundException.class, () -> {
			skiResortRepository.getSkiResort(1);
		});
	}

}
