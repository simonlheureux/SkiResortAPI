package com.ski;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/skiResorts")
public class SkiResortController {

	@Autowired
	private SkiResortService skiResortService;

	@GetMapping("/")
	public List<SkiResort> getAllSkiResorts() {
		return skiResortService.getAllSkiResorts();
	}

	@GetMapping("/{id}")
	public SkiResort getSkiResort(@PathVariable("id") long id) {

		SkiResort skiResort;
		try {
			skiResort = skiResortService.getSkiResort(id);
		} catch (ItemNotFoundException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
		}

		return skiResort;
	}

	@PostMapping("/")
	public SkiResort addSkiResort(@RequestBody SkiResort skiResort, HttpServletResponse response) {

		try {
			skiResortService.addSkiResort(skiResort);
			response.setStatus(HttpServletResponse.SC_CREATED);

		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
		}

		return skiResort;
	}

	@PutMapping("/{id}")
	SkiResort updateSkiResort(@PathVariable("id") long id, @RequestBody SkiResort skiResort,
			HttpServletResponse response) {

		SkiResort updatedSkiResort;
		try {
			updatedSkiResort = skiResortService.updateSkiResort(id, skiResort);
			response.setStatus(HttpServletResponse.SC_OK);

		} catch (ItemNotFoundException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
		} catch (ValidationException validationException) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validationException.getMessage());
		}

		return updatedSkiResort;
	}

	@DeleteMapping("/{id}")
	void deleteSkiResort(@PathVariable Long id, HttpServletResponse response) {

		try {
			skiResortService.deleteSkiResort(id);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);

		} catch (ItemNotFoundException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
		}
	}

}
