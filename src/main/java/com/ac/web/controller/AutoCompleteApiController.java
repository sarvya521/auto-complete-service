package com.ac.web.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ac.service.impl.AutoCompleteSvcFacade;
import com.ac.util.LoggerUtilities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

/**
 * Bundles all necessary APIs for auto-complete service.
 *
 * @author sarvesh
 */
@Api(tags = "Generic Auto-Complete Service")
@RestController
public class AutoCompleteApiController {

    private static final Logger LOGGER = LogManager.getLogger(AutoCompleteApiController.class);

    /**
     * @see com.ac.service.impl.AutoCompleteSvcFacade
     */
    @Autowired
    private AutoCompleteSvcFacade autoCompleteSvcFacade;

    @ApiOperation(value = "Check the health of service", response = String.class, produces = "text/plain")
    @ApiResponses(@ApiResponse(code = 200,
                               message = "OK",
                               examples = @Example(value = @ExampleProperty(value = "pong!",
                                                                            mediaType = "text/plain"))))
    @GetMapping("/api/ping")
    public String ping() {
        return "pong!";
    }

    /**
     * @param type      auto-complete component
     * @param key       non-null keyword to search
     * @param maxResult maximum number of suggestions needed
     * @return {@link java.util.List} list of {@code model} objects representing
     *         {@code type}
     */
    @ApiOperation(value = "Get a list of auto-complete suggestions for a given type", produces = "application/json")
    @ApiResponses(@ApiResponse(code = 200,
                               message = "OK",
                               examples = @Example(value = @ExampleProperty(value = "{\"id\": \"1\", \"name\": \"Aasam\"}, {\"id\": \"2\", \"name\": \"AandhraPradesh\"}]",
                                                                            mediaType = "application/json"))))
    @SuppressWarnings("rawtypes")
    @GetMapping("/api/search/{type}")
    public List
        search(@ApiParam(value = "search component",
                         example = "city",
                         required = true) @PathVariable(value = "type") final String type,
                @ApiParam(value = "search key",
                          example = "aa",
                          required = true) @RequestParam(value = "start") final String key,
                @ApiParam(value = "max number of auto-complete suggestions",
                          example = "2",
                          required = false) @RequestParam(value = "atmost", required = false) Integer maxResult) {
        LOGGER.info(LoggerUtilities.getMessage("serving auto-complete request for {} with keyword {} and limit {}",
                type, key, maxResult));
        return autoCompleteSvcFacade.search(type, key, maxResult);
    }
}
