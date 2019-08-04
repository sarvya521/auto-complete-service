package com.sp.backend.boilerplate.service;

import java.util.List;

import com.sp.backend.boilerplate.dto.PatientDTO;
import com.sp.backend.boilerplate.dto.Response;

/**
 * Bundles all CRUD Operations for Patient.
 * 
 * @since 0.0.1
 * @version 1.0
 * @author sarvesh
 */
public interface PatientService {
    Response<List<PatientDTO>> getAllPatients();

    Response<PatientDTO> getPatientById(Integer id);

    Response<PatientDTO> createPatient(PatientDTO patientDTO);

    Response<PatientDTO> updatePatient(PatientDTO patientDTO);

    Response<PatientDTO> deletePatient(Integer id);
}
