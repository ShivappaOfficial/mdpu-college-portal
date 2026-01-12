package com.sjprograming.restapi.alumni.controller;

import com.sjprograming.restapi.alumni.model.Alumni;
import com.sjprograming.restapi.alumni.repository.AlumniRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/alumni")
@CrossOrigin
public class AdminAlumniController {

    private final AlumniRepository repo;

    public AdminAlumniController(AlumniRepository repo) {
        this.repo = repo;
    }

    // 🔹 Get pending alumni
    @GetMapping("/pending")
    public List<Alumni> pending() {
        return repo.findByApprovedFalse();
    }

    // 🔹 Approve alumni
    @PutMapping("/{id}/approve")
    public void approve(@PathVariable Long id) {
        Alumni alumni = repo.findById(id).orElseThrow();
        alumni.setApproved(true);
        repo.save(alumni);
    }

    // 🔹 Delete alumni
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
