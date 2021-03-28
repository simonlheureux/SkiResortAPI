package com.ski;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class SkiResortControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SkiResortService skiResortService;

	@Test
	public void whenGetAllSkiResorts_thenReturnJsonArray() throws Exception {

		ArrayList<SkiResort> skiResorts = new ArrayList<SkiResort>();
		skiResorts.add(new SkiResort(1, "Name1", null));
		when(skiResortService.getAllSkiResorts()).thenReturn(skiResorts);

		mockMvc.perform(get("/skiResorts/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$[0].name").value("Name1"));

		verify(skiResortService, times(1)).getAllSkiResorts();
	}

	@Test
	public void whenGetSkiResort_thenReturnJsonObject() throws Exception {

		when(skiResortService.getSkiResort(1)).thenReturn(new SkiResort(1, "Name1", null));

		mockMvc.perform(get("/skiResorts/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Name1"));

		verify(skiResortService, times(1)).getSkiResort(1);
	}

	@Test
	public void whenGetInvalidSkiResort_thenReturnError() throws Exception {

		String errorMessage = "Ski resort not found for id: 999";
		when(skiResortService.getSkiResort(999)).thenThrow(new ItemNotFoundException(errorMessage));

		mockMvc.perform(get("/skiResorts/999")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(status().reason(errorMessage));

		verify(skiResortService, times(1)).getSkiResort(999);
	}

	@Test
	public void whenAddSkiResort_thenReturnJsonObject() throws Exception {

		SkiResort newSkiResort = new SkiResort(-1, "NameNew", "DescNew");
		when(skiResortService.addSkiResort(ArgumentMatchers.any())).thenReturn(newSkiResort);

		String content = new ObjectMapper().writeValueAsString(newSkiResort);

		mockMvc.perform(post("/skiResorts/").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print())
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("NameNew"))
				.andExpect(jsonPath("$.description").value("DescNew")).andExpect(jsonPath("$.id").exists());

		ArgumentCaptor<SkiResort> anySkiResort = ArgumentCaptor.forClass(SkiResort.class);
		verify(skiResortService, times(1)).addSkiResort(anySkiResort.capture());
	}

	@Test
	public void whenAddInvalidNameSkiResort_thenReturnError() throws Exception {

		String errorMessage = "Ski resort name must not be empty.";
		when(skiResortService.addSkiResort(ArgumentMatchers.any())).thenThrow(new ValidationException(errorMessage));

		SkiResort newSkiResort = new SkiResort(-1, "", null);
		String content = new ObjectMapper().writeValueAsString(newSkiResort);

		mockMvc.perform(post("/skiResorts/").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(status().reason(errorMessage));

		ArgumentCaptor<SkiResort> anySkiResort = ArgumentCaptor.forClass(SkiResort.class);
		verify(skiResortService, times(1)).addSkiResort(anySkiResort.capture());
	}

	@Test
	public void whenUpdateSkiResort_thenReturnJsonObject() throws Exception {

		SkiResort skiResort = new SkiResort(1, "NameUpdated", "DescUpdated");
		when(skiResortService.updateSkiResort(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
				.thenReturn(skiResort);

		String content = new ObjectMapper().writeValueAsString(skiResort);

		mockMvc.perform(put("/skiResorts/1").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("NameUpdated"))
				.andExpect(jsonPath("$.description").value("DescUpdated")).andExpect(jsonPath("$.id").value("1"));

		ArgumentCaptor<SkiResort> anySkiResort = ArgumentCaptor.forClass(SkiResort.class);
		ArgumentCaptor<Long> anyLong = ArgumentCaptor.forClass(Long.class);
		verify(skiResortService, times(1)).updateSkiResort(anyLong.capture(), anySkiResort.capture());
	}

	@Test
	public void whenUpdateInvalidNameSkiResort_thenReturnError() throws Exception {

		String errorMessage = "Ski resort name must not be empty.";
		SkiResort skiResort = new SkiResort(1, "", "DescUpdated");
		when(skiResortService.updateSkiResort(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
				.thenThrow(new ValidationException(errorMessage));

		String content = new ObjectMapper().writeValueAsString(skiResort);

		mockMvc.perform(put("/skiResorts/1").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(status().reason(errorMessage));

		ArgumentCaptor<SkiResort> anySkiResort = ArgumentCaptor.forClass(SkiResort.class);
		ArgumentCaptor<Long> anyLong = ArgumentCaptor.forClass(Long.class);
		verify(skiResortService, times(1)).updateSkiResort(anyLong.capture(), anySkiResort.capture());
	}

	@Test
	public void whenUpdateInvalidSkiResort_thenReturnError() throws Exception {

		String errorMessage = "Ski resort not found for id: 999";
		SkiResort skiResort = new SkiResort(999, "NameUpdated", "DescUpdated");
		when(skiResortService.updateSkiResort(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
				.thenThrow(new ItemNotFoundException(errorMessage));

		String content = new ObjectMapper().writeValueAsString(skiResort);

		mockMvc.perform(put("/skiResorts/999").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print())
				.andExpect(status().isNotFound()).andExpect(status().reason(errorMessage));

		ArgumentCaptor<SkiResort> anySkiResort = ArgumentCaptor.forClass(SkiResort.class);
		ArgumentCaptor<Long> anyLong = ArgumentCaptor.forClass(Long.class);
		verify(skiResortService, times(1)).updateSkiResort(anyLong.capture(), anySkiResort.capture());
	}

	@Test
	public void whenDeleteSkiResort_thenReturnStatus() throws Exception {

		mockMvc.perform(delete("/skiResorts/1")).andDo(print()).andExpect(status().isNoContent());

		verify(skiResortService, times(1)).deleteSkiResort(1);
	}

	@Test
	public void whenDeleteInvalidSkiResort_thenReturnError() throws Exception {

		String errorMessage = "Ski resort not found for id: 999";
		doThrow(new ItemNotFoundException(errorMessage)).when(skiResortService).deleteSkiResort(999);

		mockMvc.perform(delete("/skiResorts/999")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(status().reason(errorMessage));

		verify(skiResortService, times(1)).deleteSkiResort(999);
	}

}
