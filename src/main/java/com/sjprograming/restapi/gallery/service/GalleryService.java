package com.sjprograming.restapi.gallery.service;

import com.sjprograming.restapi.gallery.model.Gallery;
import com.sjprograming.restapi.gallery.repository.GalleryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class GalleryService {

    private static final String UPLOAD_DIR =
        System.getProperty("user.dir") + "/uploads/";

    private final GalleryRepository repo;

    public GalleryService(GalleryRepository repo) {
        this.repo = repo;
    }

    // ✅ Upload image
    public void upload(MultipartFile file) throws Exception {

        Files.createDirectories(Paths.get(UPLOAD_DIR));

        String fileName =
                System.currentTimeMillis() + "_" + file.getOriginalFilename();

        file.transferTo(new File(UPLOAD_DIR + fileName));

        Gallery g = new Gallery();
        g.setFileName(fileName);
        g.setFilePath("/uploads/" + fileName);
        g.setStatus("ACTIVE");

        repo.save(g);
    }

    // ✅ Get active images
    public List<Gallery> getActive() {
        return repo.findByStatus("ACTIVE");
    }
}
