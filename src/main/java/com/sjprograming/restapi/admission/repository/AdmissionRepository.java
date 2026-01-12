//package com.sjprograming.restapi.admission.repository;
//
//import com.sjprograming.restapi.admission.model.Admission;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface AdmissionRepository extends JpaRepository<Admission, Long> {
//}


package com.sjprograming.restapi.admission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sjprograming.restapi.admission.model.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
