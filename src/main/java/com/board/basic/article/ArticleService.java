package com.board.basic.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void create(String title, String content) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());

        this.articleRepository.save(article);

    }

    public Article getArticle(Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);
        return article.get();
    }

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public void modify(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        article.setModifyDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }
}
