package com.ski;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkiResortService {

	@Autowired
	private SkiResortRepository skiResortRepository;

	public List<SkiResort> getAllSkiResorts() {
		return skiResortRepository.getAllSkiResorts();
	}

	public SkiResort getSkiResort(long id) throws ItemNotFoundException {
		return skiResortRepository.getSkiResort(id);
	}

	public SkiResort addSkiResort(SkiResort skiResort) throws ValidationException {
		SkiResortValidator.validateSkiResort(skiResort);
		return skiResortRepository.addSkiResort(skiResort);
	}

	public SkiResort updateSkiResort(long id, SkiResort skiResort) throws ItemNotFoundException, ValidationException {
		skiResortRepository.getSkiResort(id);
		SkiResortValidator.validateSkiResort(skiResort);
		return skiResortRepository.updateSkiResort(id, skiResort);
	}

	void deleteSkiResort(long id) throws ItemNotFoundException {
		skiResortRepository.getSkiResort(id);
		skiResortRepository.deleteSkiResort(id);
	}
}
