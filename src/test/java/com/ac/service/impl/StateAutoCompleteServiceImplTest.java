package com.ac.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.ac.dto.State;
import com.ac.service.AutoCompleteService;

@SpringBootTest
public class StateAutoCompleteServiceImplTest {

	@Autowired
	@Qualifier("stateAutoCompleteService")
	AutoCompleteService<State> stateAutoCompleteService;

	@Test
	public void should_pass_search_without_limit() {
		List<State> list = stateAutoCompleteService.search("m");

		State s1 = new State();
		s1.setId(1);
		s1.setName("Maharashtra");

		State s2 = new State();
		s2.setId(3);
		s2.setName("Tamilnadu");

		State s3 = new State();
		s3.setId(7);
		s3.setName("Madhyapradesh");

		State s4 = new State();
		s4.setId(8);
		s4.setName("Aasaam");

		List<State> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(s3);
		expectedSortedList.add(s1);
		expectedSortedList.add(s4);
		expectedSortedList.add(s2);

		assertEquals(expectedSortedList, list);
	}

	@Test
	public void should_pass_search_with_limit() {
		List<State> list = stateAutoCompleteService.search("m", 3);

		State s1 = new State();
		s1.setId(1);
		s1.setName("Maharashtra");

		State s3 = new State();
		s3.setId(7);
		s3.setName("Madhyapradesh");

		State s4 = new State();
		s4.setId(8);
		s4.setName("Aasaam");

		List<State> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(s3);
		expectedSortedList.add(s1);
		expectedSortedList.add(s4);

		assertEquals(expectedSortedList, list);
	}

	@Test
	public void should_fail_search() {
		List<State> list = stateAutoCompleteService.search("z");
		assertTrue(list.isEmpty());
	}
}