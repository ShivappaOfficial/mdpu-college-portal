package com.sjprograming.restapi.batch.controller;

import java.nio.file.*;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sjprograming.restapi.batch.model.Batch;
import com.sjprograming.restapi.batch.service.BatchService;

@RestController
@RequestMapping("/api/batches")
@CrossOrigin
public class BatchController {

    private final BatchService service;

    public BatchController(BatchService service) {
        this.service = service;
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public Batch create(
            @RequestParam int year,
            @RequestParam(required = false) String title,
            @RequestParam MultipartFile image
    ) throws Exception {

        // ✅ PUBLIC STATIC PATH
        String folder = "src/main/resources/static/uploads/batches/" + year;
        Files.createDirectories(Paths.get(folder));

        String fileName = "batch_" + year + "_" + image.getOriginalFilename();
        Path path = Paths.get(folder, fileName);

        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        Batch batch = new Batch();
        batch.setYear(year);
        batch.setTitle(title);
        batch.setImageUrl("/uploads/batches/" + year + "/" + fileName);

        return service.create(batch);
    }

    @GetMapping
    public List<Batch> all() {
        return service.findAll();
    }
}
