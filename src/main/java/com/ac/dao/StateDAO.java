package com.ac.dao;

import java.util.List;

import com.ac.entity.MstState;

/**
 * Repository to handle all operations on {@code table mst_state}
 * @see com.ac.entity.MstState
 *
 * @author sarvesh
 */
public interface StateDAO {

	/**
	 * @param keyword keyword to search in {@link com.ac.entity.MstState#getName()}
	 * @return {@link java.util.List} of {@link com.ac.entity.MstState} whose {@code name} contains given {@code keyword}
	 */
    List<MstState> getStates(String keyword);

    /**
	 * @param keyword keyword to search in {@link com.ac.entity.MstState#getName()}
	 * @param maxResult maximum number of suggestions needed
	 * @return {@link java.util.List} of {@link com.ac.entity.MstState} whose {@code name} contains given {@code keyword}
	 */
	List<MstState> getStates(String keyword, int maxResult);
}
