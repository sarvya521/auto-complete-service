package com.sp.backend.boilerplate.dao;

import java.util.List;

import com.sp.backend.boilerplate.entity.MstState;

/**
 * Repository to handle all operations on {@code table mst_state}
 * 
 * @see com.sp.backend.boilerplate.entity.MstState
 *
 * @author sarvesh
 */
public interface StateDAO {

    /**
     * @param keyword keyword to search in {@link com.sp.backend.boilerplate.entity.MstState#getName()}
     * @return {@link java.util.List} of {@link com.sp.backend.boilerplate.entity.MstState} whose
     *         {@code name} contains given {@code keyword}
     */
    List<MstState> getStates(String keyword);

    /**
     * @param keyword   keyword to search in
     *                  {@link com.sp.backend.boilerplate.entity.MstState#getName()}
     * @param maxResult maximum number of suggestions needed
     * @return {@link java.util.List} of {@link com.sp.backend.boilerplate.entity.MstState} whose
     *         {@code name} contains given {@code keyword}
     */
    List<MstState> getStates(String keyword, int maxResult);
}
