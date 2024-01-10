package com.board.basic.article;

import com.board.basic.siteuser.SiteUser;
import com.board.basic.siteuser.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(ArticleForm articleForm) {
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, Principal principal) {
        SiteUser user = this.userService.getUser(principal.getName());
        this.articleService.create(title, content);
        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable("id") Integer id, Principal principal) {
        SiteUser user = this.userService.getUser(principal.getName());
        Article article = this.articleService.getArticle(id);
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        return "article_form";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String articleModify(@Valid ArticleForm articleForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
        SiteUser user = this.userService.getUser(principal.getName());
        Article article = this.articleService.getArticle(id);
        this.articleService.modify(article, articleForm.getTitle(), articleForm.getContent());
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String articleDelete(Principal principal, @PathVariable("id") Integer id ) {
        SiteUser user = this.userService.getUser(principal.getName());
        Article article = this.articleService.getArticle(id);
        this.articleService.delete(article);
        return "redirect:/";
    }
}



