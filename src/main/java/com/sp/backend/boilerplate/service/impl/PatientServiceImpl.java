package com.sp.backend.boilerplate.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sp.backend.boilerplate.dto.PatientDTO;
import com.sp.backend.boilerplate.dto.Response;
import com.sp.backend.boilerplate.service.PatientService;

import lombok.extern.log4j.Log4j2;

/**
 * Implementation of {@link PractitionerService}.
 * 
 * @since 0.0.1
 * @version 1.0
 * @author sarvesh
 */
@Log4j2
@Service
public class PatientServiceImpl implements PatientService {
    
    @Override
    public Response<List<PatientDTO>> getAllPatients() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<PatientDTO> getPatientById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<PatientDTO> createPatient(PatientDTO patientDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<PatientDTO> updatePatient(PatientDTO patientDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<PatientDTO> deletePatient(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    

}
