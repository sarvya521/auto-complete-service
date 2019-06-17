package com.ac.service.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ac.dto.State;

public class StateNameComparatorTest {

    @Test
    public void should_pass_sorting() {
        List<State> list = new ArrayList<>();
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

        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);

        list.sort(new StateNameComparator("m"));

        List<State> expectedSortedList = new ArrayList<>();
        expectedSortedList.add(s3);
        expectedSortedList.add(s1);
        expectedSortedList.add(s4);
        expectedSortedList.add(s2);

        assertEquals(expectedSortedList, list);
    }

}
