package com.sjprograming.restapi.gallery.repository;

import com.sjprograming.restapi.gallery.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findByStatus(String status);
}
