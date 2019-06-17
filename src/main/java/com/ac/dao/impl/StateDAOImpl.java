package com.ac.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.ac.entity.MstState;
import com.ac.dao.StateDAO;

/**
 * Implementation of {@link com.ac.dao.StateDAO}
 *
 * @author sarvesh
 */
@Repository
public class StateDAOImpl implements StateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MstState> getStates(String keyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstState> cq = cb.createQuery(MstState.class);

        Root<MstState> state = cq.from(MstState.class);

        Predicate namePredicate = cb.like(cb.lower(state.get("name")), "%" + keyword.toLowerCase() + "%");
        cq.where(namePredicate);

        TypedQuery<MstState> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MstState> getStates(String keyword, int maxResult) {
        List<MstState> result = new ArrayList<>();
        getStatesStartsWithKeyword(keyword, maxResult, result);
        if (result.size() < maxResult) {
            getStatesContainsKeyword(keyword, maxResult, result);
        }
        return result;
    }

    private void getStatesStartsWithKeyword(String keyword, int maxResult, List<MstState> result) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstState> cq = cb.createQuery(MstState.class);

        Root<MstState> state = cq.from(MstState.class);

        Predicate namePredicate = cb.like(cb.lower(state.get("name")), keyword.toLowerCase() + "%");
        cq.where(namePredicate);

        cq.orderBy(cb.asc(state.get("name")));

        TypedQuery<MstState> query = entityManager.createQuery(cq);
        query.setFirstResult(0)
            .setMaxResults(maxResult - result.size());
        result.addAll(query.getResultList());
    }

    private void getStatesContainsKeyword(String keyword, int maxResult, List<MstState> result) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MstState> cq = cb.createQuery(MstState.class);

        Root<MstState> state = cq.from(MstState.class);

        List<String> stateNames = result.stream()
            .map(c -> c.getName())
            .collect(Collectors.toList());

        Predicate namePredicate = cb.and(cb.not(state.get("name")
            .in(stateNames)), cb.like(cb.lower(state.get("name")), "%" + keyword.toLowerCase() + "%"));
        cq.where(namePredicate);

        cq.orderBy(cb.asc(state.get("name")));

        TypedQuery<MstState> query = entityManager.createQuery(cq);
        query.setFirstResult(0)
            .setMaxResults(maxResult - result.size());
        result.addAll(query.getResultList());
    }

}
