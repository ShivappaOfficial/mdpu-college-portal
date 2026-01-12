package com.sjprograming.restapi.batch.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sjprograming.restapi.batch.model.Batch;
import com.sjprograming.restapi.batch.repository.BatchRepository;

@Service
public class BatchService {

    private final BatchRepository repo;

    public BatchService(BatchRepository repo) {
        this.repo = repo;
    }

    public Batch create(Batch batch) {
        if (repo.existsByYear(batch.getYear())) {
            throw new RuntimeException("Batch already exists");
        }
        return repo.save(batch);
    }

    public List<Batch> findAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "year"));
    }
}
