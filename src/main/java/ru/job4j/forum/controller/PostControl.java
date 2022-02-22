package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
public class PostControl {
    private final PostService postService;

    public PostControl(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/create")
    public String create() {
        return "/create";
    }

    @GetMapping("/update")
    public String update(@RequestParam ("id") int id, Model model) {
        model.addAttribute("post", postService.findPostById(id));
        return "/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post, HttpServletRequest request) {
        if (post.getId() != 0) {
            Post rsl = postService.findPostById(post.getId());
            rsl.setName(post.getName());
            rsl.setDescription(post.getDescription());
            postService.savePost(rsl);
            return "redirect:/";
        }
        post.setUser((User) request.getSession().getAttribute("user"));
        post.setCreated(Calendar.getInstance());
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/post")
    public String post(@RequestParam ("id") int id, Model model) {
        model.addAttribute("post", postService.findPostById(id));
        return "/post";
    }

    @PostMapping("/comment/save")
    public String saveComment(@RequestParam ("id") int postId, String text,
                              HttpServletRequest request) {
        postService.saveComment(Comment.of(text, (User) request.getSession().getAttribute("user")), postId);
        return "redirect:/post?id=" + postId;
    }
}
