package com.sp.backend.boilerplate.web.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sp.backend.boilerplate.dto.City;
import com.sp.backend.boilerplate.service.helper.CityNameComparator;
import com.sp.backend.boilerplate.service.impl.AutoCompleteSvcFacade;

/**
 * @author sarvesh
 *
 */
class AutoCompleteApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AutoCompleteSvcFacade autoCompleteSvcFacade;

    @InjectMocks
    private AutoCompleteApiController apiController;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(apiController)
            .build();
    }

    @Test
    public void searchCity_shouldPass_KeyForOnlyCity() throws Exception {
        final String type = "city";
        final String key = "n";

        List<City> expectedSortedList = new ArrayList<>();
        City c1 = new City();
        c1.setId(5);
        c1.setName("Pune");
        
        City c2 = new City();
        c2.setId(6);
        c2.setName("Noida");
        
        City c3 = new City();
        c3.setId(3);
        c3.setName("New Delhi");
        
        City c4 = new City();
        c4.setId(7);
        c4.setName("Chennai");
        
        City c5 = new City();
        c5.setId(4);
        c5.setName("Banglore");
        
        expectedSortedList.add(c1);
        expectedSortedList.add(c2);
        expectedSortedList.add(c3);
        expectedSortedList.add(c4);
        expectedSortedList.add(c5);

        expectedSortedList.sort(new CityNameComparator(key));

        when(autoCompleteSvcFacade.search(type, key, null)).thenReturn(expectedSortedList);

        mockMvc.perform(get("/api/v1.0/search/" + type).param("start", key)
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].name", is("New Delhi")))
            .andExpect(jsonPath("$[1].name", is("Noida")))
            .andExpect(jsonPath("$[2].name", is("Banglore")))
            .andExpect(jsonPath("$[3].name", is("Chennai")))
            .andExpect(jsonPath("$[4].name", is("Pune")));

        verify(autoCompleteSvcFacade, times(1)).search(type, key, null);
        verifyNoMoreInteractions(autoCompleteSvcFacade);
    }

    @Test
    public void searchCity_shouldPass_KeyWithTypeAsCity() throws Exception {
        final String type = "city";
        final String key = "n";

        List<City> expectedSortedList = new ArrayList<>();
        City c1 = new City();
        c1.setId(5);
        c1.setName("Pune");
        
        City c2 = new City();
        c2.setId(6);
        c2.setName("Noida");
        
        City c3 = new City();
        c3.setId(3);
        c3.setName("New Delhi");
        
        City c4 = new City();
        c4.setId(7);
        c4.setName("Chennai");
        
        City c5 = new City();
        c5.setId(4);
        c5.setName("Banglore");
        
        expectedSortedList.add(c1);
        expectedSortedList.add(c2);
        expectedSortedList.add(c3);
        expectedSortedList.add(c4);
        expectedSortedList.add(c5);

        expectedSortedList.sort(new CityNameComparator(key));

        when(autoCompleteSvcFacade.search(type, key, null)).thenReturn(expectedSortedList);

        mockMvc.perform(get("/api/v2.0/search/" + type).param("start", key)
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].name", is("New Delhi")))
            .andExpect(jsonPath("$[1].name", is("Noida")))
            .andExpect(jsonPath("$[2].name", is("Banglore")))
            .andExpect(jsonPath("$[3].name", is("Chennai")))
            .andExpect(jsonPath("$[4].name", is("Pune")));

        verify(autoCompleteSvcFacade, times(1)).search(type, key, null);
        verifyNoMoreInteractions(autoCompleteSvcFacade);
    }

    @Test
    public void searchCity_shouldPass_NoLimit() throws Exception {
        final String type = "city";
        final String key = "n";

        List<City> expectedSortedList = new ArrayList<>();
        City c1 = new City();
        c1.setId(5);
        c1.setName("Pune");
        
        City c2 = new City();
        c2.setId(6);
        c2.setName("Noida");
        
        City c3 = new City();
        c3.setId(3);
        c3.setName("New Delhi");
        
        City c4 = new City();
        c4.setId(7);
        c4.setName("Chennai");
        
        City c5 = new City();
        c5.setId(4);
        c5.setName("Banglore");
        
        expectedSortedList.add(c1);
        expectedSortedList.add(c2);
        expectedSortedList.add(c3);
        expectedSortedList.add(c4);
        expectedSortedList.add(c5);

        expectedSortedList.sort(new CityNameComparator(key));

        when(autoCompleteSvcFacade.search(type, key, null)).thenReturn(expectedSortedList);

        mockMvc.perform(get("/api/v2.1/search/" + type).param("start", key)
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].name", is("New Delhi")))
            .andExpect(jsonPath("$[1].name", is("Noida")))
            .andExpect(jsonPath("$[2].name", is("Banglore")))
            .andExpect(jsonPath("$[3].name", is("Chennai")))
            .andExpect(jsonPath("$[4].name", is("Pune")));

        verify(autoCompleteSvcFacade, times(1)).search(type, key, null);
        verifyNoMoreInteractions(autoCompleteSvcFacade);
    }

    @Test
    public void searchCity_shouldPass_givenLimit() throws Exception {
        final String type = "city";
        final String key = "n";
        final Integer maxResult = 3;
        
        List<City> expectedSortedList = new ArrayList<>();
        City c1 = new City();
        c1.setId(3);
        c1.setName("New Delhi");
        
        City c2 = new City();
        c2.setId(6);
        c2.setName("Noida");
        
        City c3 = new City();
        c3.setId(4);
        c3.setName("Banglore");
        
        expectedSortedList.add(c1);
        expectedSortedList.add(c2);
        expectedSortedList.add(c3);

        when(autoCompleteSvcFacade.search(type, key, maxResult)).thenReturn(expectedSortedList);

        mockMvc.perform(get("/api/v2.1/search/" + type).param("start", key)
            .param("atmost", maxResult.toString())
            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(maxResult)))
            .andExpect(jsonPath("$[0].name", is("New Delhi")))
            .andExpect(jsonPath("$[1].name", is("Noida")))
            .andExpect(jsonPath("$[2].name", is("Banglore")));

        verify(autoCompleteSvcFacade, times(1)).search(type, key, maxResult);
        verifyNoMoreInteractions(autoCompleteSvcFacade);
    }
}
