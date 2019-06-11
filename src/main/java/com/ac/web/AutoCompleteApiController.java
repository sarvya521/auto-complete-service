package com.ac.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ac.service.impl.AutoCompleteSvcFacade;
import com.ac.util.LoggerUtilities;


/**
 * Bundles all necessary APIs for auto-complete service.
 *
 * @author sarvesh
 */
@RestController
public class AutoCompleteApiController {

	private static final Logger LOGGER = LogManager.getLogger(AutoCompleteApiController.class);

	@Autowired
    private ApplicationEventPublisher eventPublisher;

	/**
	 * @see com.ac.service.impl.AutoCompleteSvcFacade
	 */
	@Autowired
	private AutoCompleteSvcFacade autoCompleteSvcFacade;

	@GetMapping("/api/ping")
	public String ping() {
		return "pong!";
	}

	/**
	 * @param type auto-complete component
	 * @param key  non-null keyword to search
	 * @param maxResult maximum number of suggestions needed
	 * @return {@link java.util.List} list of {@code model} objects representing
	 *         {@code type}
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/api/search/{type}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List search(@PathVariable(value = "type") final String type, @RequestParam(value = "start") final String key,
			@RequestParam(value = "atmost", required = false) Integer maxResult) {
		LOGGER.info(LoggerUtilities.getMessage("serving auto-complete request for {} with keyword {} and limit {}", type, key, maxResult));
		return autoCompleteSvcFacade.search(type, key, maxResult);
	}
}
