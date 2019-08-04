package com.sp.backend.boilerplate.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sp.backend.boilerplate.dao.StateDAO;
import com.sp.backend.boilerplate.dto.State;
import com.sp.backend.boilerplate.entity.MstState;
import com.sp.backend.boilerplate.service.AutoCompleteService;
import com.sp.backend.boilerplate.service.helper.StateNameComparator;
import com.sp.backend.boilerplate.util.SynchronizedInMemoryCache;

/**
 * Implementation of {@link com.sp.backend.boilerplate.service.AutoCompleteService} for type State.
 * See {@link com.sp.backend.boilerplate.dto.State}
 *
 * @author sarvesh
 */
@Service
@Qualifier("stateAutoCompleteService")
public class StateAutoCompleteServiceImpl implements AutoCompleteService<State> {

    private static final Logger LOGGER = LogManager.getLogger(StateAutoCompleteServiceImpl.class);

    @Autowired
    private StateDAO stateDAO;

    @Autowired
    private ApplicationContext context;

    /**
     * See {@link com.sp.backend.boilerplate.util.SynchronizedInMemoryCache}
     */
    private SynchronizedInMemoryCache<String, List<State>> cache;

    /**
     * Initializes cache for storing auto-complete search results
     * {@code List<State>} against search keyword {@code key}. Caching mechanism is
     * implemented using {@link com.sp.backend.boilerplate.util.SynchronizedInMemoryCache}. Settings
     * needed for initializing {@link com.sp.backend.boilerplate.util.SynchronizedInMemoryCache} are
     * configured in {@code environment properties}.
     *
     * <p>
     * Caching can only be activated by all three mandatory settings
     * {@code cache.state.timeToLive}, {@code cache.state.timerInterval}, and
     * {@code cache.state.maxItems}.
     *
     * <p>
     * If state specific cache settings are not found in
     * {@code environment properties}, then default cache settings are used. Default
     * settings are {@code cache.default.timeToLive},
     * {@code cache.default.timerInterval}, and {@code cache.default.maxItems}
     */
    @PostConstruct
    public void initCache() {
        LOGGER.info("initializing cache for state auto-complete results");
        String timeToLive;
        String timerInterval;
        String maxItems;
        timeToLive = context.getEnvironment()
            .getProperty("cache.state.timeToLive");
        if (timeToLive != null) {
            timerInterval = context.getEnvironment()
                .getProperty("cache.state.timerInterval");
            maxItems = context.getEnvironment()
                .getProperty("cache.state.maxItems");
        } else {
            LOGGER.info(
                    "state specific cache settings not found in application.properties. Applying default cache settings");
            timeToLive = context.getEnvironment()
                .getProperty("cache.default.timeToLive");
            timerInterval = context.getEnvironment()
                .getProperty("cache.default.timerInterval");
            maxItems = context.getEnvironment()
                .getProperty("cache.default.maxItems");
        }
        if (timeToLive == null || timerInterval == null || maxItems == null) {
            LOGGER.error("deafult cache settings not found");
            return;
        }
        LOGGER.info("cache for state auto-complete results initialized");
        cache = new SynchronizedInMemoryCache<>(Long.valueOf(timeToLive), Long.valueOf(timerInterval),
                Integer.valueOf(maxItems));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * {@link com.sp.backend.boilerplate.service.helper.StateNameComparator} is custom
     * {@link java.util.Comparator} used for sorting.
     *
     * @return {@link java.util.List} list of {@link com.sp.backend.boilerplate.dto.State}
     */
    @Transactional
    @Override
    public List<State> search(final String key) {
        List<State> result = null;
        if (cache != null && (result = cache.get(key)) != null) {
            LOGGER.info("returning auto-complete state cache result for key {}", key);
            return result;
        }
        List<MstState> states = stateDAO.getStates(key);
        result = states.stream()
            .map(mstState ->
                {
                    State state = new State();
                    prepareModelFromDto(state, mstState);
                    return state;
                })
            .collect(Collectors.toList());

        result.sort(new StateNameComparator(key));

        if (cache != null) {
            cache.put(key, result);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link java.util.List} list of {@link com.sp.backend.boilerplate.dto.State}
     */
    @Transactional
    @Override
    public List<State> search(final String key, final Integer maxResult) {
        List<State> result = null;
        if (cache != null && (result = cache.get(key)) != null) {
            LOGGER.info("returning auto-complete state cache result for key {}", key);
            return result.size() > maxResult ? result.subList(0, maxResult) : result;
        }
        List<MstState> states = stateDAO.getStates(key, maxResult);
        result = states.stream()
            .map(mstState ->
                {
                    State state = new State();
                    prepareModelFromDto(state, mstState);
                    return state;
                })
            .collect(Collectors.toList());

        if (cache != null) {
            cache.put(key, result);
        }
        return result;
    }

    private void prepareModelFromDto(final State stateBean, final MstState stateDto) {
        stateBean.setId(stateDto.getId());
        stateBean.setName(stateDto.getName());
    }
}
