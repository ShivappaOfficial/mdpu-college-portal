

package com.sjprograming.restapi.admission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sjprograming.restapi.admission.model.Admission;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    boolean existsByMobileAndEmail(String mobile, String email);

    // Admin view all
    List<Admission> findAllByOrderByIdDesc();
    
    Optional<Admission> findByAdmissionIdAndNameIgnoreCase(
    	    String admissionId,
    	    String name
    	);

}
