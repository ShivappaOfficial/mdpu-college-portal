package com.sjprograming.restapi.admission.service;

import com.sjprograming.restapi.admission.model.Admission;
import com.sjprograming.restapi.admission.repository.AdmissionRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final JavaMailSender mailSender;

    // ✅ Constructor Injection (NO Lombok)
    public AdmissionService(
            AdmissionRepository admissionRepository,
            JavaMailSender mailSender
    ) {
        this.admissionRepository = admissionRepository;
        this.mailSender = mailSender;
    }

    // ✅ SAVE APPLICATION
    public Admission saveApplication(Admission admission) {

        // ❌ Prevent duplicate application
        boolean exists = admissionRepository
                .existsByMobileAndEmail(
                        admission.getMobile(),
                        admission.getEmail()
                );

        if (exists) {
            throw new RuntimeException(
                "Duplicate application not allowed for same mobile & email"
            );
        }

        // ✅ Calculate Percentage
        double percentage =
            (admission.getObtainedMarks() * 100.0)
            / admission.getTotalMarks();

        admission.setPercentage(percentage);

        // Save first time to generate ID
        Admission saved = admissionRepository.save(admission);

        // ✅ Generate Admission ID
        String admissionId =
            generateAdmissionId(
                admission.getSslcRegNo(),
                saved.getId()
            );

        saved.setAdmissionId("ADM-" + admissionId);
        admissionRepository.save(saved);

        // ✅ Send confirmation mail
        sendMail(saved);
        System.out.println("mail Sent Successfully");

        return saved;
    }

    // ✅ Admission ID Logic
    private String generateAdmissionId(String sslcRegNo, Long id) {
        String prefix = sslcRegNo.substring(0, 6);
        String suffix = String.format("%04d", id);
        return prefix + suffix;
    }

    // ✅ Mail Sending Logic
    private void sendMail(Admission admission) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                new MimeMessageHelper(message);

            helper.setFrom(
                new InternetAddress(
                    "noreply@mdrpuhsg.com",
                    "MDR PU College"
                )
            );

            helper.setTo(admission.getEmail());
            helper.setSubject(
                "Admission Application Received – MDR PU College"
            );

            helper.setText(
                "Dear " + admission.getName() + ",\n\n" +
                "Thank you for applying for admission to MDR PU College, Hiresindhogi.\n\n" +
                "Admission ID: " + admission.getAdmissionId() + "\n" +
                "Course Applied: " + admission.getCombination() + "\n" +
                "Mobile Number: " + admission.getMobile() + "\n\n" +
                "Our admission team will contact you shortly.\n\n" +
                "Warm regards,\n" +
                "MDR PU College\n" +
                "Hiresindhogi, Karnataka\n" +
                "Phone: +91 98765 43210",
                false
            );

            mailSender.send(message);
            System.out.println("message"+message);
            System.out.println("Mail Sent Successfully");

        } catch (Exception e) {
            System.out.println(
                "⚠️ Email failed but admission saved: " + e.getMessage()
            );
        }
    }
    
 // ADMIN: View all applications
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAllByOrderByIdDesc();
    }

    // ADMIN: Approve
    public void approveAdmission(Long id) {
        Admission a = admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found"));
        a.setStatus("APPROVED");
        admissionRepository.save(a);
    }

    // ADMIN: Reject
    public void rejectAdmission(Long id) {
        Admission a = admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found"));
        a.setStatus("REJECTED");
        admissionRepository.save(a);
    }

    // ADMIN: Delete (optional)
    public void deleteAdmission(Long id) {
    	admissionRepository.deleteById(id);
    }
}
