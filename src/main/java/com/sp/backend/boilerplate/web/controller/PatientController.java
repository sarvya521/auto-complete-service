package com.sp.backend.boilerplate.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.backend.boilerplate.dto.PatientDTO;
import com.sp.backend.boilerplate.dto.Response;
import com.sp.backend.boilerplate.service.PatientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Bundles all CRUD APIs for Patient.
 * 
 * @since 0.0.1
 * @version 1.0
 * @author sarvesh
 */
@Api(tags = "Patient API")
@RestController
@RequestMapping("/patient/api/v1.0")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @ApiOperation(value = "View a list of available patients", notes = "Latest API", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    @GetMapping
    public Response<List<PatientDTO>> getAllPatients() {
        return patientService.getAllPatients();
    }

    @ApiOperation(value = "Get an Patient by Id", notes = "Latest API", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    @GetMapping("/{id}")
    public Response<PatientDTO> getPatientById(
            @ApiParam(value = "Patient id from which patient object will retrieve", required = true) 
                @PathVariable(value = "id") Integer patientId) {
        return patientService.getPatientById(patientId);
    }

    @ApiOperation(value = "Add an patient", notes = "Latest API", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Resource created successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to perform this action on the resource") })
    @PostMapping
    public Response<PatientDTO> createPatient(@ApiParam(value = "patient object store in database table",
                                                        required = true) @RequestBody PatientDTO patientDTO) {
        return patientService.createPatient(patientDTO);
    }

    @ApiOperation(value = "Update an patient", notes = "Latest API", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Resource updated successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to perform this action on the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to update is not found") })
    @PutMapping
    public Response<PatientDTO>
        updatePatient(@ApiParam(value = "Update patient object", required = true) @RequestBody PatientDTO patientDTO) {
        return patientService.updatePatient(patientDTO);
    }

    @ApiOperation(value = "Delete an patient", notes = "Latest API", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Resource deleted successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to perform this action on the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to delete is not found") })
    @DeleteMapping("/{id}")
    public Response<PatientDTO> deletePatient(
            @ApiParam(value = "Patient Id from which patient object will delete from database table",
                      required = true) @PathVariable(value = "id") Integer patientId) {
        return patientService.deletePatient(patientId);
    }

}
