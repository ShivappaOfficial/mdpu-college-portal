package com.sjprograming.restapi.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sjprograming.restapi.batch.model.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    boolean existsByYear(int year);
}
