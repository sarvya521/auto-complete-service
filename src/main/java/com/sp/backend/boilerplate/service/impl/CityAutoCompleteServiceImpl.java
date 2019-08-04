package com.sp.backend.boilerplate.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sp.backend.boilerplate.dao.CityDAO;
import com.sp.backend.boilerplate.dto.City;
import com.sp.backend.boilerplate.entity.MstCity;
import com.sp.backend.boilerplate.exception.AutoCompleteSvcException;
import com.sp.backend.boilerplate.service.AutoCompleteService;
import com.sp.backend.boilerplate.service.helper.CityNameComparator;

/**
 * Implementation of {@link com.ac.service.AutoCompleteService} for type City.
 * See {@link com.ac.dto.City}
 *
 * @author sarvesh
 */
@Service
@Qualifier("cityAutoCompleteService")
public class CityAutoCompleteServiceImpl implements AutoCompleteService<City> {

    @Autowired
    private CityDAO cityDAO;

    /**
     * {@inheritDoc}
     *
     * <p>
     * {@link com.ac.service.helper.CityNameComparator} is custom
     * {@link java.util.Comparator} used for sorting.
     *
     * @return {@link java.util.List} list of {@link com.ac.dto.City}
     */
    @Transactional
    @Override
    public List<City> search(final String key) {
        List<City> result = null;
        List<MstCity> cities = cityDAO.getCities(key);
        result = cities.stream()
            .map(mstCity ->
                {
                    City city = new City();
                    prepareModelFromDto(city, mstCity);
                    return city;
                })
            .collect(Collectors.toList());

        result.sort(new CityNameComparator(key));
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link java.util.List} list of {@link com.ac.dto.City}
     */
    @Transactional
    @Override
    public List<City> search(final String key, final Integer maxResult) {
        if (maxResult <= 0) {
            throw new AutoCompleteSvcException("maxResult should be greater than zero");
        }
        List<City> result = null;
        List<MstCity> cities = cityDAO.getCities(key, maxResult);

        result = cities.stream()
            .map(mstCity ->
                {
                    City city = new City();
                    prepareModelFromDto(city, mstCity);
                    return city;
                })
            .collect(Collectors.toList());
        return result;
    }

    private void prepareModelFromDto(final City cityBean, final MstCity cityDto) {
        cityBean.setId(cityDto.getId());
        cityBean.setName(cityDto.getName());
    }
}
