//package com.sjprograming.restapi.notification;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void sendAdmissionMail(String to, String name, String course) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Admission Confirmation");
//        message.setText(
//                "Hello " + name +
//                ",\n\nYour admission for " + course +
//                " is confirmed.\n\nMDR PU College"
//        );
//
//        mailSender.send(message);
//    }
//}
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
            Long admissionId,
            String course,
            String mobile
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            // 👇 THIS IS THE KEY PART
            helper.setFrom(new InternetAddress(
                "noreply@mdrpuhsg.com",
                "MDR PU College"
            ));

            helper.setTo(toEmail);
            helper.setSubject("Admission Application Received – MDR PU College");

            helper.setText(
                "Dear " + studentName + ",\n\n" +
                "Thank you for applying for admission to MDR PU College, Hiresindhogi.\n\n" +
                "We have successfully received your application with the following details:\n\n" +
                "Admission ID: ADM-" + admissionId + "\n" +
                "Course Applied: " + course + "\n" +
                "Mobile Number: " + mobile + "\n\n" +
                "Our admission team will contact you shortly.\n\n" +
                "Warm regards,\n" +
                "MDR PU College\n" +
                "Hiresindhogi, Karnataka\n" +
                "Phone: +91 98765 43210",
                false
            );

            mailSender.send(message);
            System.out.println("Mail Sent Succesfully");

        } catch (Exception e) {
            System.out.println("⚠️ Email failed but admission saved: " + e.getMessage());
        }
    }
}

