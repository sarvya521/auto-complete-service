package com.ac.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ac.dao.StateDAO;
import com.ac.entity.MstState;

@SpringBootTest
public class StateDAOImplTest {

    @Autowired
    private StateDAO stateDAO;

    @Test
    public void should_pass_getStates_without_limit() {
        List<MstState> list = stateDAO.getStates("m");
        assertEquals(4, list.size());

        MstState s1 = new MstState();
        s1.setId(1);
        s1.setName("Maharashtra");

        MstState s2 = new MstState();
        s2.setId(3);
        s2.setName("Tamilnadu");

        MstState s3 = new MstState();
        s3.setId(7);
        s3.setName("Madhyapradesh");

        MstState s4 = new MstState();
        s4.setId(8);
        s4.setName("Aasaam");

        List<MstState> expectedList = new ArrayList<>();
        expectedList.add(s3);
        expectedList.add(s1);
        expectedList.add(s4);
        expectedList.add(s2);

        assertTrue(list.containsAll(expectedList));
    }

    @Test
    public void should_pass_getStates_with_limit() {
        List<MstState> list = stateDAO.getStates("m", 3);
        assertEquals(3, list.size());

        MstState s1 = new MstState();
        s1.setId(1);
        s1.setName("Maharashtra");

        MstState s3 = new MstState();
        s3.setId(7);
        s3.setName("Madhyapradesh");

        MstState s4 = new MstState();
        s4.setId(8);
        s4.setName("Aasaam");

        List<MstState> expectedSortedList = new ArrayList<>();
        expectedSortedList.add(s3);
        expectedSortedList.add(s1);
        expectedSortedList.add(s4);

        assertTrue(list.containsAll(expectedSortedList));
    }

    @Test
    public void should_fail_getStates() {
        List<MstState> list = stateDAO.getStates("z");
        assertTrue(list.isEmpty());
    }

}
