package com.sjprograming.restapi.batch.controller;

import java.nio.file.*;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sjprograming.restapi.batch.model.Batch;
import com.sjprograming.restapi.batch.service.BatchService;
import com.sjprograming.restapi.cloudinary.service.CloudinaryService;

@RestController
@RequestMapping("/api/batches")
@CrossOrigin
public class BatchController {

    private final BatchService service;
    private final CloudinaryService cloudinaryService;

    public BatchController(BatchService service,
                           CloudinaryService cloudinaryService) {
        this.service = service;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public Batch create(
            @RequestParam int year,
            @RequestParam(required = false) String title,
            @RequestParam MultipartFile image
    ) throws Exception {

        // ✅ Upload to Cloudinary
        String imageUrl = cloudinaryService.upload(image, "batches");

        Batch batch = new Batch();
        batch.setYear(year);
        batch.setTitle(title);
        batch.setImageUrl(imageUrl);

        return service.create(batch);
    }

    @GetMapping
    public List<Batch> all() {
        return service.findAll();
    }
}
