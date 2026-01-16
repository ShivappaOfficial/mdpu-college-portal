package com.sjprograming.restapi.notification;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

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
        String body =
                "Dear " + safeName(studentName) + ",\n\n" +
                "Your admission application has been successfully received.\n\n" +
                "Admission ID: " + admissionId + "\n" +
                "Course Applied: " + course + "\n" +
                "Mobile: " + mobile + "\n\n" +
                "Regards,\nMDR PU College";

        sendMail(toEmail, "Admission Application Received – MDR PU College", body);
    }

    // ================= APPROVED =================
    public void sendApprovedMail(
            String toEmail,
            String studentName,
            String admissionId
    ) {
        sendMail(
                toEmail,
                "Admission Approved – MDR PU College",
                "Dear " + safeName(studentName) + ",\n\n" +
                "🎉 Congratulations! Your admission has been APPROVED.\n\n" +
                "Admission ID: " + admissionId + "\n\n" +
                "Regards,\nMDR PU College"
        );
    }

    // ================= REJECTED =================
    public void sendRejectedMail(
            String toEmail,
            String studentName,
            String admissionId
    ) {
        sendMail(
                toEmail,
                "Admission Update – MDR PU College",
                "Dear " + safeName(studentName) + ",\n\n" +
                "We regret to inform you that your admission was NOT approved.\n\n" +
                "Admission ID: " + admissionId + "\n\n" +
                "Regards,\nMDR PU College"
        );
    }

    // ================= CORE SEND METHOD =================
    private void sendMail(String to, String subject, String contentText) {
        try {
            Email from = new Email("shivuyk57@gmail.com", "MDR PU College");
            Email toEmail = new Email(to);
            Content content = new Content("text/plain", contentText);

            Mail mail = new Mail(from, subject, toEmail, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("✅ Mail sent. Status: " + response.getStatusCode());

        } catch (Exception e) {
            System.err.println("❌ SendGrid API mail failed");
            e.printStackTrace();
        }
    }
}
