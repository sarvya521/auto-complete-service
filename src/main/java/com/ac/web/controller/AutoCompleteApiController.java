package com.ac.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ac.constant.AutoCompleteComponent;
import com.ac.service.impl.AutoCompleteSvcFacade;
import com.ac.util.LoggerUtilities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.extern.log4j.Log4j2;

/**
 * Bundles all necessary APIs for auto-complete service.
 *
 * @author sarvesh
 */
@Api(tags = "Generic Auto-Complete Service")
@Log4j2
@RestController
public class AutoCompleteApiController {
    /**
     * @see com.ac.service.impl.AutoCompleteSvcFacade
     */
    @Autowired
    private AutoCompleteSvcFacade autoCompleteSvcFacade;

    /**
     * @param type      auto-complete component
     * @param key       non-null keyword to search
     * @param maxResult maximum number of suggestions needed
     * @return {@link java.util.List} list of {@code model} objects representing
     *         {@code type}
     */
    @ApiOperation(value = "Get a list of auto-complete suggestions for a given type", notes = "Latest API", produces = "application/json")
    @ApiResponses(@ApiResponse(code = 200,
                               message = "OK",
                               examples = @Example(value = @ExampleProperty(value = "{\"id\": \"1\", \"name\": \"Aasam\"}, {\"id\": \"2\", \"name\": \"AandhraPradesh\"}]",
                                                                            mediaType = "application/json"))))
    @SuppressWarnings("rawtypes")
    @GetMapping("/api/v2.1/search/{type}")
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
        log.info(LoggerUtilities.getMessage("serving auto-complete request for {} with keyword {} and limit {}",
                type, key, maxResult));
        return autoCompleteSvcFacade.search(type, key, maxResult);
    }
    
    /**
     * @param type      auto-complete component
     * @param key       non-null keyword to search
     * @return {@link java.util.List} list of {@code model} objects representing
     *         {@code type}
     */
    @ApiOperation(value = "Get a list of auto-complete suggestions for a given type", notes = "version 2.0", produces = "application/json")
    @ApiResponses(@ApiResponse(code = 200,
                               message = "OK",
                               examples = @Example(value = @ExampleProperty(value = "{\"id\": \"1\", \"name\": \"Aasam\"}, {\"id\": \"2\", \"name\": \"AandhraPradesh\"}]",
                                                                            mediaType = "application/json"))))
    @SuppressWarnings("rawtypes")
    @GetMapping("/api/v2.0/search/{type}")
    public List
        search(@ApiParam(value = "search component",
                         example = "city",
                         required = true) @PathVariable(value = "type") final String type,
                @ApiParam(value = "search key",
                          example = "aa",
                          required = true) @RequestParam(value = "start") final String key) {
        log.info(LoggerUtilities.getMessage("serving auto-complete request for {} with keyword {}",
                type, key));
        return autoCompleteSvcFacade.search(type, key, null);
    }
    
    /**
     * @deprecated
     * @param key       non-null keyword to search
     * @return {@link java.util.List} list of {@code City} objects
     */
    @ApiOperation(value = "Get a list of auto-complete suggestions for city", notes = "Deprecated API, kept for legacy support, Expiry: 31 Dec 2020", produces = "application/json")
    @ApiResponses(@ApiResponse(code = 200,
                               message = "OK",
                               examples = @Example(value = @ExampleProperty(value = "{\"id\": \"1\", \"name\": \"Aasam\"}, {\"id\": \"2\", \"name\": \"AandhraPradesh\"}]",
                                                                            mediaType = "application/json"))))
    @Deprecated(since="2.0", forRemoval=true)
    @SuppressWarnings("rawtypes")
    @GetMapping("/api/v1.0/search/city")
    public List
        searchCity(@ApiParam(value = "search key",
                          example = "aa",
                          required = true) @RequestParam(value = "start") final String key) {
        log.info(LoggerUtilities.getMessage("serving auto-complete request for city with keyword {}", key));
        return autoCompleteSvcFacade.search(AutoCompleteComponent.CITY, key, null);
    }
}
