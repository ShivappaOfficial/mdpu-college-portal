package com.sjprograming.restapi.notification;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Async
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

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
                    new MimeMessageHelper(message, "UTF-8");

            // ✅ MUST MATCH spring.mail.username
            helper.setFrom(
                    new InternetAddress(
                            "shivuyk57@gmail.com",
                            "MDR PU College"
                    )
            );

            helper.setTo(toEmail);
            helper.setSubject("Admission Application Received – MDR PU College");

            helper.setText(
                    "Dear " + studentName + ",\n\n" +
                    "Your admission application has been received successfully.\n\n" +
                    "Admission ID: " + admissionId + "\n" +
                    "Course Applied: " + course + "\n" +
                    "Mobile Number: " + mobile + "\n\n" +
                    "Our admission team will contact you shortly.\n\n" +
                    "Warm regards,\n" +
                    "MDR PU College\n" +
                    "Hiresindhogi, Karnataka",
                    false
            );

            mailSender.send(message);
            System.out.println("✅ Email sent to " + toEmail);

        } catch (Exception e) {
            System.out.println("⚠️ Email failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
