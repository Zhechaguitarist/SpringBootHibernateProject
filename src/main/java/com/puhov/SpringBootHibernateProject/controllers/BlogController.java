package com.puhov.SpringBootHibernateProject.controllers;

import com.puhov.SpringBootHibernateProject.config.LoginPassword;
import com.puhov.SpringBootHibernateProject.config.SecurityConfiguration;
import com.puhov.SpringBootHibernateProject.entity.Post;
import com.puhov.SpringBootHibernateProject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping(path = "/blog")
@RequiredArgsConstructor
public class BlogController {

    private final PostRepository postRepository;

    @GetMapping
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blogs/blog-main";
    }

    @GetMapping(path = "/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping(path = "/add")
    public String blogAdd(Model model) {
        return "blogs/blog-add";
    }

    @PostMapping(path = "/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text,
                              Model model) {
        Post post = new Post();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setCreated(OffsetDateTime.now());
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping(path = "/{id}")
    public String blogDetails(@PathVariable(value = "id") Long id,
                              Model model) {
        if (!postRepository.existsById(id)) return "redirect:/blog";
        Optional<Post> post = postRepository.findById(id);
        List<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd MMMM yyyy", Locale.ENGLISH);
        String createdDateTime = result.get(0).getCreated().format(dateTimeFormatter);
        OffsetDateTime changed = result.get(0).getChanged();
        String changedDateTime = changed != null ? changed.format(dateTimeFormatter) : "Статью ни разу не изменяли";
        model.addAttribute("created_date_time", createdDateTime);
        model.addAttribute("changed_date_time", changedDateTime);
        return "blogs/blog-details";
    }

    @GetMapping(path = "/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Long id,
                              Model model) {
        if (!postRepository.existsById(id)) return "redirect:/blog";
        Optional<Post> post = postRepository.findById(id);
        List<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blogs/blog-edit";
    }

    @PostMapping(path = "/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") Long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text,
                                 Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setChanged(OffsetDateTime.now());
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping(path = "/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") Long id,
                                 Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam String login,
                        @RequestParam String password) {
        LoginPassword.login = login;
        LoginPassword.password = password;
        return "redirect:/blog";
    }

    @GetMapping(path = "/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String registration(@RequestParam String firstname,
                               @RequestParam String lastname,
                               @RequestParam String login,
                               @RequestParam String password) {
        return "registration";
    }
}
