package com.sjprograming.restapi.news.repository;

import com.sjprograming.restapi.news.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByExpiryDateIsNullOrExpiryDateAfterOrderByPinnedDescCreatedAtDesc(
    		 LocalDate today
    );
}
