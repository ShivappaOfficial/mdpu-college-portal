package com.sjprograming.restapi.notification;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Async
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    private static final String FROM_EMAIL = "shivuyk57@gmail.com";
    private static final String FROM_NAME  = "MDR PU College";

    // ================= SAFETY =================
    private String safeName(String name) {
        return (name == null || name.trim().isEmpty())
                ? "Student"
                : name.trim();
    }

    // ================= CORE SEND =================
    private void sendMail(String to, String subject, String htmlContent) {
        Email from = new Email(FROM_EMAIL, FROM_NAME);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", htmlContent);

        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("📧 SENDGRID STATUS: " + response.getStatusCode());
            System.out.println("📧 SENDGRID HEADERS: " + response.getHeaders());

        } catch (IOException e) {
            System.err.println("❌ SENDGRID FAILED");
            e.printStackTrace();
        }
    }

    // ================= APPLICATION SUBMITTED =================
    public void sendAdmissionMail(
            String toEmail,
            String studentName,
            String admissionId,
            String course,
            String mobile
    ) {
        sendMail(
                toEmail,
                "Admission Application Received – MDR PU College",
                admissionSubmittedTemplate(
                        safeName(studentName),
                        admissionId,
                        course,
                        mobile
                )
        );
    }

    // ================= APPROVED =================
    public void sendApprovedMail(
            String toEmail,
            String studentName,
            String admissionId
    ) {
        sendMail(
                toEmail,
                "🎉 Admission Approved – MDR PU College",
                approvedTemplate(
                        safeName(studentName),
                        admissionId
                )
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
                "Admission Status – MDR PU College",
                rejectedTemplate(
                        safeName(studentName),
                        admissionId
                )
        );
    }

    // ================= HTML TEMPLATES =================

    private String admissionSubmittedTemplate(
            String name,
            String admissionId,
            String course,
            String mobile
    ) {
        return """
       <html>
<body style="font-family: Arial, sans-serif; line-height:1.6">

<h2>Dear %s,</h2>

<p>
We are delighted to inform you that your admission application has been
<strong>received successfully</strong>.
</p>

<p>
<b>Admission ID:</b> %s<br>
<b>Course:</b> %s<br>
<b>Mobile:</b> %s
</p>

<hr>

<p>
🎓 <strong>Welcome to the beginning of a bright academic journey!</strong>
</p>

<p>
At <b>MDR PU Science College</b>, we believe that every student has the potential
to achieve excellence. Our institution is committed to providing
<strong>quality education, strong academic foundations, and holistic development</strong>
to help you succeed in both academics and life.
</p>

<p>
By choosing MDR PU College, you are stepping into an environment that
encourages curiosity, innovation, discipline, and confidence.
Our experienced faculty, modern infrastructure, and student-friendly campus
are designed to support your dreams and aspirations.
</p>

<p>
We strongly encourage you to stay focused, motivated, and passionate about
your goals. Your dedication combined with our guidance will help you build
a strong future and open doors to higher education and career opportunities.
</p>

<p>
📞 Our admission team will contact you shortly with further details
regarding the next steps in the admission process.
</p>

<p>
If you have any questions or need assistance, please feel free to reach out to us.
We are always happy to support you.
</p>

<br>

<p>
<b>MDR PU College</b><br>
Hiresindhogi, Karnataka
</p>

</body>
</html>

        """.formatted(name, admissionId, course, mobile);
    }

    private String approvedTemplate(String name, String admissionId) {
        return """
        <html>
        <body style="font-family:Arial">
          <h2>🎉 Congratulations %s!</h2>
          <p>Your admission has been <b style="color:green">APPROVED</b>.</p>
          <p><b>Admission ID:</b> %s</p>
          <br>
          <p>Please visit the college office for further process.</p>
          <br>
          <b>MDR PU College</b>
        </body>
        </html>
        """.formatted(name, admissionId);
    }

    private String rejectedTemplate(String name, String admissionId) {
        return """
        <html>
        <body style="font-family:Arial">
          <h2>Dear %s,</h2>
          <p>We regret to inform you that your admission was <b style="color:red">NOT APPROVED</b>.</p>
          <p><b>Admission ID:</b> %s</p>
          <br>
          <p>You may contact the office for clarification.</p>
          <br>
          <b>MDR PU College</b>
        </body>
        </html>
        """.formatted(name, admissionId);
    }
}
