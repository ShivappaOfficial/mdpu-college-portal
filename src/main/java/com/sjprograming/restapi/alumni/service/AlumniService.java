package com.sjprograming.restapi.alumni.service;

import com.sjprograming.restapi.alumni.model.Alumni;
import com.sjprograming.restapi.alumni.repository.AlumniRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AlumniService {

    private final AlumniRepository repository;

    public AlumniService(AlumniRepository repository) {
        this.repository = repository;
    }

    public Alumni save(Alumni alumni) {
        return repository.save(alumni);
    }
    
    public List<Alumni> getApprovedAlumni() {
        return repository.findByApprovedTrue();
    }
    
    // ✅ REQUIRED FOR CLICK BATCH
    public List<Alumni> getApprovedAlumniByYear(int year) {
        return repository.findByYearOfPassoutAndApprovedTrue(year);
    }
    
//    public List<Alumni> getByYear(int year){
//        return repository.findByApprovedTrueAndYearOfPassout(year);
//    }

}
