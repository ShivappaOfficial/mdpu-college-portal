package com.sjprograming.restapi.alumni.controller;

import com.sjprograming.restapi.alumni.model.Alumni;
import com.sjprograming.restapi.alumni.service.AlumniService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alumni")
@CrossOrigin
public class AlumniController {

    private final AlumniService service;

    public AlumniController(AlumniService service) {
        this.service = service;
    }

    @PostMapping
    public Alumni submit(@RequestBody Alumni alumni) {
        return service.save(alumni);
    }
    
    @GetMapping("/public")
    public List<Alumni> publicAlumni() {
        return service.getApprovedAlumni();
    }
    
    @GetMapping("/public/by-year/{year}")
    public List<Alumni> alumniByYear(@PathVariable int year) {
        return service.getApprovedAlumniByYear(year);
    }
    
//    @GetMapping("/public/by-year/{year}")
//    public List<Alumni> byYear(@PathVariable int year){
//        return service.getByYear(year);
//    }

    
}
