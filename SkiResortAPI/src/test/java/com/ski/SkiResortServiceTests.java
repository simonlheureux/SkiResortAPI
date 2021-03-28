package com.ski;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SkiResortServiceTests {

	@Autowired
	private SkiResortService skiResortService;

	@MockBean
	private SkiResortRepository skiResortRepository;

	@Test
	void whenNewSkiResortInfoInvalid_thenThrowException() {

		assertThrows(ValidationException.class, () -> {
			skiResortService.addSkiResort(new SkiResort(-1, null, null));
		});
	}

	@Test
	void whenUpdatedSkiResortInfoInvalid_thenThrowException() throws ItemNotFoundException {

		SkiResort skiResort1 = new SkiResort(1, "Name1", "Desc1");
		when(skiResortRepository.getSkiResort(1)).thenReturn(skiResort1);

		assertThrows(ValidationException.class, () -> {
			skiResortService.updateSkiResort(1, new SkiResort(1, null, null));
		});

		verify(skiResortRepository, times(1)).getSkiResort(1);
	}

	@Test
	void whenUpdatedSkiResortDoesntExist_thenThrowException() throws ItemNotFoundException {

		when(skiResortRepository.getSkiResort(999))
				.thenThrow(new ItemNotFoundException("Ski resort not found for id: 999"));

		assertThrows(ItemNotFoundException.class, () -> {
			skiResortService.updateSkiResort(999, new SkiResort(999, "Name1", null));
		});

		verify(skiResortRepository, times(1)).getSkiResort(999);
	}

	@Test
	void whenDeleteSkiResortDoesntExist_thenThrowException() throws ItemNotFoundException {

		when(skiResortRepository.getSkiResort(999))
				.thenThrow(new ItemNotFoundException("Ski resort not found for id: 999"));

		assertThrows(ItemNotFoundException.class, () -> {
			skiResortService.deleteSkiResort(999);
		});

		verify(skiResortRepository, times(1)).getSkiResort(999);
	}

	@Test
	void whenSkiResortDoesntExist_thenThrowException() throws ItemNotFoundException {

		when(skiResortRepository.getSkiResort(999))
				.thenThrow(new ItemNotFoundException("Ski resort not found for id: 999"));

		assertThrows(ItemNotFoundException.class, () -> {
			skiResortService.getSkiResort(999);
		});

		verify(skiResortRepository, times(1)).getSkiResort(999);
	}
}
