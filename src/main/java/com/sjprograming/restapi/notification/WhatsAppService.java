package com.sjprograming.restapi.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
    name = "whatsapp.enabled",
    havingValue = "true",
    matchIfMissing = false
)
public class WhatsAppService {

    @Value("${twilio.account.sid}")
    private String sid;

    @Value("${twilio.auth.token}")
    private String token;

    @Value("${twilio.whatsapp.from}")
    private String from;

    @PostConstruct
    public void init() {
        Twilio.init(sid, token);
    }

    public void sendMessage(String mobile, String name, String course) {

        Message.creator(
                new PhoneNumber("whatsapp:+91" + mobile),
                new PhoneNumber(from),
                "Hello " + name +
                ", your admission for " + course +
                " is confirmed. - MDR PU College"
        ).create();
    }
}
