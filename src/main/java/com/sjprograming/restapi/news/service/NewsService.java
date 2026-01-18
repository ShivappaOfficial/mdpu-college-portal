package com.sjprograming.restapi.news.service;

import com.sjprograming.restapi.news.model.News;
import com.sjprograming.restapi.news.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository repo;

    public NewsService(NewsRepository repo) {
        this.repo = repo;
    }

    /* ===== PUBLIC ===== */

    public List<News> getActiveNews() {
        return repo.findByExpiryDateIsNullOrExpiryDateAfterOrderByPinnedDescCreatedAtDesc(
                LocalDateTime.now()
        );
    }

    public News getNewsById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
    }

    /* ===== ADMIN ===== */

    public News createNews(News news) {
        return repo.save(news);
    }

    public News updateNews(Long id, News updated) {
        News existing = getNewsById(id);

        existing.setTitle(updated.getTitle());
        existing.setShortDescription(updated.getShortDescription());
        existing.setContent(updated.getContent());
        existing.setPinned(updated.isPinned());
        existing.setExpiryDate(updated.getExpiryDate());

        return repo.save(existing);
    }

    public void deleteNews(Long id) {
        repo.deleteById(id);
    }
}
