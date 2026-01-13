package com.sjprograming.restapi.gallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sjprograming.restapi.gallery.model.Gallery;
import com.sjprograming.restapi.gallery.repository.GalleryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class GalleryService {

    private final Cloudinary cloudinary;
    private final GalleryRepository repo;

    public GalleryService(Cloudinary cloudinary, GalleryRepository repo) {
        this.cloudinary = cloudinary;
        this.repo = repo;
    }

    public void upload(MultipartFile file) throws Exception {

        Map uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "folder", "mdpu/gallery"
            )
        );

        String imageUrl = uploadResult.get("secure_url").toString();

        Gallery g = new Gallery();
        g.setFileName(file.getOriginalFilename());
        g.setFilePath(imageUrl);
        g.setStatus("ACTIVE");

        repo.save(g);
    }
    public List<Gallery> getActive() {
        return repo.findByStatus("ACTIVE");
    }
}
