package com.sp.backend.boilerplate.dao;

import com.sp.backend.boilerplate.entity.MstCity;

import java.util.List;

/**
 * Repository to handle all operations on {@code table mst_city}
 * 
 * @see com.sp.backend.boilerplate.entity.MstCity
 *
 * @author sarvesh
 */
public interface CityDAO {

    /**
     * @param keyword keyword to search in {@link com.sp.backend.boilerplate.entity.MstCity#getName()}
     * @return {@link java.util.List} of {@link com.sp.backend.boilerplate.entity.MstCity} whose
     *         {@code name} contains given {@code keyword}
     */
    List<MstCity> getCities(String keyword);

    /**
     * @param keyword   keyword to search in {@link com.sp.backend.boilerplate.entity.MstCity#getName()}
     * @param maxResult maximum number of suggestions needed
     * @return {@link java.util.List} of {@link com.sp.backend.boilerplate.entity.MstCity} whose
     *         {@code name} contains given {@code keyword}
     */
    List<MstCity> getCities(String keyword, int maxResult);

}
