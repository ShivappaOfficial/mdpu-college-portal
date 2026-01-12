package com.sjprograming.restapi.gallery.controller;

import com.sjprograming.restapi.gallery.model.Gallery;
import com.sjprograming.restapi.gallery.service.GalleryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin
public class GalleryController {

    private final GalleryService service;

    public GalleryController(GalleryService service) {
        this.service = service;
    }

    // ✅ Admin upload
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        service.upload(file);
        return "Uploaded";
    }

    // ✅ Home + Admin table
    @GetMapping
    public List<Gallery> getAll() {
        return service.getActive();
    }
}
