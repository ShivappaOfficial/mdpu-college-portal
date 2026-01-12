//package com.sjprograming.restapi.admission.service;
//
//import com.sjprograming.restapi.admission.model.Admission;
//import com.sjprograming.restapi.admission.repository.AdmissionRepository;
//import com.sjprograming.restapi.notification.EmailService;
//import com.sjprograming.restapi.notification.WhatsAppService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AdmissionService {
//
//    private final AdmissionRepository repository;
//    private final EmailService emailService;
//    private final WhatsAppService whatsAppService;
//
//    public AdmissionService(
//            AdmissionRepository repository,
//            EmailService emailService,
//            @Autowired(required = false) WhatsAppService whatsAppService
//    ) {
//        this.repository = repository;
//        this.emailService = emailService;
//        this.whatsAppService = whatsAppService;
//    }
//
//
//
//    public Admission submit(Admission admission) {
//
//        Admission saved = repository.save(admission);
//
//        if (admission.getEmail() != null && !admission.getEmail().isEmpty()) {
//            emailService.sendAdmissionMail(
//                    admission.getEmail(),
//                    admission.getName(),
//                    admission.getCourse()
//            );
//        }
//
////        whatsAppService.sendMessage(
////                admission.getMobile(),
////                admission.getName(),
////                admission.getCourse()
////        );
//
//        return saved;
//    }
//}

package com.sjprograming.restapi.admission.service;

import org.springframework.stereotype.Service;

import com.sjprograming.restapi.admission.model.Admission;
import com.sjprograming.restapi.admission.repository.AdmissionRepository;
import com.sjprograming.restapi.notification.EmailService;

@Service
public class AdmissionService {

    private final AdmissionRepository repository;
    private final EmailService emailService;

    public AdmissionService(AdmissionRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public Admission submit(Admission admission) {

        // 1️⃣ SAVE TO DB (MOST IMPORTANT)
        Admission saved = repository.save(admission);

        // 2️⃣ SEND EMAIL (SAFE)
        if (saved.getEmail() != null && !saved.getEmail().isEmpty()) {
            emailService.sendAdmissionMail(
                saved.getEmail(),
                saved.getName(),
                saved.getId(),
                saved.getCourse(),
                saved.getMobile()
            );
        }

        return saved;
    }
}
