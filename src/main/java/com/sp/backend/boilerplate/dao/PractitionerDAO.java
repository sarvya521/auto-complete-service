package com.sp.backend.boilerplate.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sp.backend.boilerplate.entity.Practitioner;

@Repository
public interface PractitionerDAO extends CrudRepository<Practitioner, Integer>{
    

}
