package com.sp.backend.boilerplate.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.sp.backend.boilerplate.dao.CityDAO;
import com.sp.backend.boilerplate.dto.City;
import com.sp.backend.boilerplate.entity.MstCity;
import com.sp.backend.boilerplate.exception.AutoCompleteSvcException;
import com.sp.backend.boilerplate.service.helper.CityNameComparator;

@SpringBootTest
public class CityAutoCompleteServiceImplTest {

    @InjectMocks
    CityAutoCompleteServiceImpl cityAutoCompleteService;

    @Mock
    private CityDAO cityDAOMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void search_ShouldPass_ValidSearch() {
        String keyword = "n";

        List<MstCity> list = new ArrayList<>();
        list.add(mockMstCity(5, "Pune"));
        list.add(mockMstCity(6, "Noida"));
        list.add(mockMstCity(3, "New Delhi"));
        list.add(mockMstCity(7, "Chennai"));
        list.add(mockMstCity(4, "Banglore"));
        when(cityDAOMock.getCities(keyword)).thenReturn(list);

        List<City> actualList = cityAutoCompleteService.search(keyword);
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
        
        expectedSortedList.sort(new CityNameComparator(keyword));
        assertEquals(expectedSortedList, actualList);

        verify(cityDAOMock, times(1)).getCities(keyword);
    }

    @Test
    public void search_ShouldPass_NotFound() {
        String keyword = "trivandrum";
        when(cityDAOMock.getCities(keyword)).thenReturn(new ArrayList<>());
        List<City> actualList = cityAutoCompleteService.search(keyword);
        assertTrue(actualList.isEmpty());
    }

    @Test
    public void search_ShouldPass_Sort() {
        String keyword = "n";

        List<MstCity> list = new ArrayList<>();
        list.add(mockMstCity(5, "Pune"));
        list.add(mockMstCity(6, "Noida"));
        list.add(mockMstCity(3, "New Delhi"));
        list.add(mockMstCity(7, "Chennai"));
        list.add(mockMstCity(4, "Banglore"));
        when(cityDAOMock.getCities(keyword)).thenReturn(list);

        List<City> actualList = cityAutoCompleteService.search(keyword);
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
        expectedSortedList.sort(new CityNameComparator(keyword));

        assertEquals(expectedSortedList, actualList);
    }

    @Test
    public void searchMaxResult_ShouldPass_ValidSearch() {
        String keyword = "n";
        Integer maxResult = 3;

        List<MstCity> list = new ArrayList<>();
        list.add(mockMstCity(5, "Pune"));
        list.add(mockMstCity(6, "Noida"));
        list.add(mockMstCity(3, "New Delhi"));
        
        when(cityDAOMock.getCities(keyword, maxResult)).thenReturn(list);

        List<City> actualList = cityAutoCompleteService.search(keyword, maxResult);
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
        expectedSortedList.add(c1);
        expectedSortedList.add(c2);
        expectedSortedList.add(c3);

        assertEquals(expectedSortedList, actualList);

        verify(cityDAOMock, times(1)).getCities(keyword, maxResult);
    }

    @Test
    public void searchMaxResult_ShouldPass_NotFound() {
        String keyword = "trivandrum";
        Integer maxResult = 3;

        when(cityDAOMock.getCities(keyword, maxResult)).thenReturn(new ArrayList<>());
        List<City> actualList = cityAutoCompleteService.search(keyword);

        assertTrue(actualList.isEmpty());
    }

    @Test
    public void searchMaxResult_ThrowInvalidInputException_InvalidMaxResult() {
        String keyword = "trivandrum";
        Integer maxResult = 0;

        when(cityDAOMock.getCities(keyword)).thenReturn(new ArrayList<>());

        AutoCompleteSvcException exception = Assertions.assertThrows(AutoCompleteSvcException.class, () ->
            {
                cityAutoCompleteService.search(keyword, maxResult);
            });
        assertEquals("maxResult should be greater than zero", exception.getMessage());
    }

    private MstCity mockMstCity(Integer id, String name) {
        MstCity mstCityMock = mock(MstCity.class);
        when(mstCityMock.getId()).thenReturn(id);
        when(mstCityMock.getName()).thenReturn(name);
        return mstCityMock;
    }
}
