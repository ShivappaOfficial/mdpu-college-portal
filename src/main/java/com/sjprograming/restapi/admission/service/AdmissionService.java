package com.sjprograming.restapi.admission.service;

import com.sjprograming.restapi.admission.model.Admission;
import com.sjprograming.restapi.admission.repository.AdmissionRepository;
import com.sjprograming.restapi.notification.EmailService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final EmailService emailService;

    public AdmissionService(
            AdmissionRepository admissionRepository,
            EmailService emailService
    ) {
        this.admissionRepository = admissionRepository;
        this.emailService = emailService;
    }

    // ✅ SAVE APPLICATION
    public Admission saveApplication(Admission admission) {

        // ❌ Prevent duplicate application
        boolean exists = admissionRepository.existsByMobileAndEmail(
                admission.getMobile(),
                admission.getEmail()
        );

        if (exists) {
            throw new RuntimeException(
                "Duplicate application not allowed for same mobile & email"
            );
        }

        // ❌ Safety check
        if (admission.getObtainedMarks() == null || admission.getTotalMarks() == null) {
            throw new RuntimeException("Marks are required");
        }

        // ✅ Calculate percentage
        double percentage =
                (admission.getObtainedMarks() * 100.0)
                / admission.getTotalMarks();
        admission.setPercentage(percentage);

        // ✅ Set created time BEFORE save
        admission.setCreatedAt(LocalDateTime.now());

        // ✅ First save → get DB ID
        Admission saved = admissionRepository.save(admission);

        // ✅ Generate Admission ID
        String admissionId = generateAdmissionId(
                admission.getSslcRegNo(),
                saved.getId()
        );

        saved.setAdmissionId("ADM-" + admissionId);
        admissionRepository.save(saved);

        // ✅ SEND EMAIL (ASYNC & SAFE)
        emailService.sendAdmissionMail(
                saved.getEmail(),
                saved.getName(),
                saved.getAdmissionId(),
                saved.getCombination(),
                saved.getMobile()
        );

        return saved;
    }

    // ✅ Admission ID logic (SAFE)
    private String generateAdmissionId(String sslcRegNo, Long id) {
        String prefix = sslcRegNo.length() >= 6
                ? sslcRegNo.substring(0, 6)
                : sslcRegNo;

        String suffix = String.format("%04d", id);
        return prefix + suffix;
    }

    // ================= ADMIN =================

    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAllByOrderByIdDesc();
    }

    public void approveAdmission(Long id) {
        Admission a = admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found"));
        a.setStatus("APPROVED");
        admissionRepository.save(a);
    }

    public void rejectAdmission(Long id) {
        Admission a = admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found"));
        a.setStatus("REJECTED");
        admissionRepository.save(a);
    }

    public void deleteAdmission(Long id) {
        admissionRepository.deleteById(id);
    }
}
