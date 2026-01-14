package com.sjprograming.restapi.notification;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private String safeName(String name) {
        return (name == null || name.trim().isEmpty())
                ? "Student"
                : name.trim();
    }

    // ================= APPLICATION SUBMITTED =================
    public void sendAdmissionMail(
            String toEmail,
            String studentName,
            String admissionId,
            String course,
            String mobile
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(new InternetAddress(
                    "shivuyk57@gmail.com",
                    "MDR PU College"
            ));

            helper.setTo(toEmail);

            helper.setSubject("Admission Application Received – MDR PU College");

            helper.setText(
                    "Dear " + safeName(studentName) + ",\n\n" +
                    "Your admission application has been successfully received.\n\n" +
                    "Admission ID: " + admissionId + "\n" +
                    "Course Applied: " + course + "\n" +
                    "Mobile Number: " + mobile + "\n\n" +
                    "Our admission team will contact you shortly.\n\n" +
                    "Regards,\n" +
                    "MDR PU College\n" +
                    "Hiresindhogi, Karnataka",
                    false
            );

            mailSender.send(message);
            System.out.println("✅ Admission email sent to " + toEmail);

        } catch (Exception e) {
            System.err.println("⚠️ Email failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= APPROVED =================
    public void sendApprovedMail(
            String toEmail,
            String studentName,
            String admissionId
    ) {
        sendStatusMail(
                toEmail,
                studentName,
                admissionId,
                "APPROVED",
                "Congratulations! Your admission has been approved."
        );
    }

    // ================= REJECTED =================
    public void sendRejectedMail(
            String toEmail,
            String studentName,
            String admissionId
    ) {
        sendStatusMail(
                toEmail,
                studentName,
                admissionId,
                "REJECTED",
                "We regret to inform you that your admission was not approved."
        );
    }

    private void sendStatusMail(
            String toEmail,
            String studentName,
            String admissionId,
            String status,
            String messageText
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(new InternetAddress(
                    "shivuyk57@gmail.com",
                    "MDR PU College"
            ));

            helper.setTo(toEmail);
            helper.setSubject("Admission Status Update – MDR PU College");

            helper.setText(
                    "Dear " + safeName(studentName) + ",\n\n" +
                    messageText + "\n\n" +
                    "Admission ID: " + admissionId + "\n" +
                    "Status: " + status + "\n\n" +
                    "Regards,\n" +
                    "MDR PU College",
                    false
            );

            mailSender.send(message);
            System.out.println("✅ Status mail sent: " + status);

        } catch (Exception e) {
            System.err.println("⚠️ Status email failed: " + e.getMessage());
        }
    }
}
