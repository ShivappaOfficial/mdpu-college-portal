package com.sjprograming.restapi.alumni.repository;

import com.sjprograming.restapi.alumni.model.Alumni;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {

    // 🔹 Admin – pending approval
    List<Alumni> findByApprovedFalse();

    // 🔹 Public – all approved alumni
    List<Alumni> findByApprovedTrue();

    // ✅ REQUIRED: Public – approved alumni by batch year
    List<Alumni> findByYearOfPassoutAndApprovedTrue(int year);
    
//    List<Alumni> findByApprovedTrueAndYearOfPassout(int year);
}
