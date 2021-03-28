package com.ski;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SkiResortRepository {

	private static Map<Long, SkiResort> skiResorts = new HashMap<Long, SkiResort>();

	static {
		skiResorts.put(1L, new SkiResort(1, "Name1", "Description1"));
		skiResorts.put(2L, new SkiResort(2, "Name2", "Description2"));
		skiResorts.put(3L, new SkiResort(3, "Name3", "Description3"));
	}

	public List<SkiResort> getAllSkiResorts() {
		return new ArrayList<SkiResort>(skiResorts.values());
	}

	public SkiResort getSkiResort(long id) throws ItemNotFoundException {

		SkiResort skiResort = skiResorts.get(id);

		if (skiResort == null) {
			throw new ItemNotFoundException(String.format("Ski resort not found for id: %d", id));
		}

		return skiResort;
	}

	public SkiResort addSkiResort(SkiResort newSkiResort) {
		long id = generateId();
		newSkiResort.setId(id);
		skiResorts.put(newSkiResort.getId(), newSkiResort);

		return newSkiResort;
	}

	private long generateId() {
		return Collections.max(skiResorts.keySet()) + 1;
	}

	public SkiResort updateSkiResort(long id, SkiResort skiResort) {

		skiResort.setId(id);
		skiResorts.put(id, skiResort);

		return skiResorts.get(id);
	}

	public void deleteSkiResort(long id) {
		skiResorts.remove(id);
	}

}
