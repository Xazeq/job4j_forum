package ru.job4j.forum.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import ru.job4j.forum.service.SecurityService;

import java.util.Calendar;

@Controller
public class PostControl {
    private final PostService postService;
    private final SecurityService securityService;

    public PostControl(PostService postService, SecurityService securityService) {
        this.postService = postService;
        this.securityService = securityService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/create";
    }

    @GetMapping("/update")
    public String update(@RequestParam ("id") int id, Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("post", postService.findPostById(id));
        return "/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post) {
        if (post.getId() != 0) {
            Post rsl = postService.findPostById(post.getId());
            rsl.setName(post.getName());
            rsl.setDescription(post.getDescription());
            postService.savePost(rsl);
            return "redirect:/";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(securityService.findUserByUsername(user.getUsername()));
        post.setCreated(Calendar.getInstance());
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/post")
    public String post(@RequestParam ("id") int id, Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("post", postService.findPostById(id));
        return "/post";
    }

    @PostMapping("/comment/save")
    public String saveComment(@RequestParam ("id") int postId, String text) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postService.saveComment(Comment.of(text,
                securityService.findUserByUsername(user.getUsername())), postId);
        return "redirect:/post?id=" + postId;
    }
}
