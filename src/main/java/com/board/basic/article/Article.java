package com.board.basic.article;

import com.board.basic.siteuser.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne
    private SiteUser author;
}
