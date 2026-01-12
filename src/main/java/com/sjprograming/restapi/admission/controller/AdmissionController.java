//package com.sjprograming.restapi.admission.controller;
//
//import java.io.PrintWriter;
//import java.util.List;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.sjprograming.restapi.admission.model.Admission;
//import com.sjprograming.restapi.admission.repository.AdmissionRepository;
//
//@RestController
//@RequestMapping("/api/admissions")
//public class AdmissionController {
//
//    private final AdmissionRepository admissionRepository;
//
//    public AdmissionController(AdmissionRepository admissionRepository) {
//        this.admissionRepository = admissionRepository;
//    }
//
//    // ✅ GET ALL ADMISSIONS
//    @GetMapping
//    public List<Admission> getAll() {
//        return admissionRepository.findAll(
//                Sort.by(Sort.Direction.DESC, "id")
//        );
//    }
//
//    // ✅ DELETE ADMISSION
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        admissionRepository.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
//
//    // ✅ EXPORT TO EXCEL (CSV)
//    @GetMapping("/export")
//    public void export(HttpServletResponse response) throws Exception {
//
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader(
//                "Content-Disposition",
//                "attachment; filename=admissions.csv"
//        );
//
//        List<Admission> list = admissionRepository.findAll();
//        PrintWriter writer = response.getWriter();
//
//        writer.println("ID,Name,Mobile,Email,Course,Address");
//
//        for (Admission a : list) {
//            writer.println(
//                a.getId() + "," +
//                a.getName() + "," +
//                a.getMobile() + "," +
//                a.getEmail() + "," +
//                a.getCourse() + "," +
//                a.getAddress()
//            );
//        }
//
//        writer.flush();
//        writer.close();
//    }
//}



package com.sjprograming.restapi.admission.controller;

import org.springframework.web.bind.annotation.*;

import com.sjprograming.restapi.admission.model.Admission;
import com.sjprograming.restapi.admission.service.AdmissionService;

@RestController
@RequestMapping("/api/admissions")
@CrossOrigin
public class AdmissionController {

    private final AdmissionService service;

    public AdmissionController(AdmissionService service) {
        this.service = service;
    }

    @PostMapping
    public Admission apply(@RequestBody Admission admission) {
        return service.submit(admission);
    }

}
