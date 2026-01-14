package com.sjprograming.restapi.admission.controller;

import com.sjprograming.restapi.admission.model.Admission;
import com.sjprograming.restapi.admission.repository.AdmissionRepository;
import com.sjprograming.restapi.admission.service.AdmissionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admission")
@CrossOrigin
public class AdmissionController {

    private final AdmissionService admissionService;
    private final AdmissionRepository admissionRepository;

    // ✅ Constructor Injection
    public AdmissionController(
            AdmissionService admissionService,
            AdmissionRepository admissionRepository
    ) {
        this.admissionService = admissionService;
        this.admissionRepository = admissionRepository;
    }

    // ✅ SUBMIT APPLICATION
    @PostMapping("/apply")
    public ResponseEntity<?> apply(
            @RequestBody Admission admission
    ) {
        try {
            Admission saved =
                admissionService.saveApplication(admission);
            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(e.getMessage());
        }
    }

 // 🔐 ADMIN – VIEW ALL
    @GetMapping("/admin/all")
    public List<Admission> getAllAdmissions() {
        return admissionService.getAllAdmissions();
    }

    // 🔐 ADMIN – APPROVE
    @PutMapping("/admin/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id) {
    	admissionService.approveAdmission(id);
        return ResponseEntity.ok("Approved");
    }

    // 🔐 ADMIN – REJECT
    @PutMapping("/admin/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id) {
    	admissionService.rejectAdmission(id);
        return ResponseEntity.ok("Rejected");
    }

    // 🔐 ADMIN – DELETE (optional)
    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
    	admissionService.deleteAdmission(id);
    }

    // PUBLIC – CHECK STATUS (FINAL VERSION)
    @PostMapping("/status")
    public ResponseEntity<?> checkStatus(
            @RequestParam String admissionId
    ) {
        return admissionRepository
                .findByAdmissionId(admissionId.trim())
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() ->
                    ResponseEntity.badRequest().body("No application found")
                );
    }



    // ✅ CHECK ADMISSION STATUS
//    @PostMapping("/status")
//    public ResponseEntity<?> checkStatus(
//    		@RequestParam String admissionId,
//            @RequestParam String name
//    ) {
//        Optional<Admission> result =
//        		admissionRepository.findByAdmissionIdAndNameIgnoreCase(
//        				admissionId.trim(),
//        	            name.trim()
//        			);
//
//        if (result.isPresent()) {
//            return ResponseEntity.ok(result.get());
//        } else {
//            return ResponseEntity
//                .badRequest()
//                .body("No application found");
//        }
//    }
}
