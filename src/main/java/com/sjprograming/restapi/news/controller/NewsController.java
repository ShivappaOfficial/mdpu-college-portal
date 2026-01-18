package com.sjprograming.restapi.news.controller;

import com.sjprograming.restapi.news.model.News;
import com.sjprograming.restapi.news.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }

    /* ===== PUBLIC ===== */

    @GetMapping("/public")
    public List<News> publicNews() {
        return service.getActiveNews();
    }

    @GetMapping("/public/{id}")
    public News view(@PathVariable Long id) {
        return service.getNewsById(id);
    }

    /* ===== ADMIN (JWT REQUIRED) ===== */

    @PostMapping("/admin")
    public News create(@RequestBody News news) {
        return service.createNews(news);
    }

    @PutMapping("/admin/{id}")
    public News update(@PathVariable Long id, @RequestBody News news) {
        return service.updateNews(id, news);
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteNews(id);
    }
}
