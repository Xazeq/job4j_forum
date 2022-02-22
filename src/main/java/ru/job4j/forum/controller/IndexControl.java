package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.forum.service.PostService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexControl {
    private final PostService postService;

    public IndexControl(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request,
                        Model model) {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("posts", postService.findAllPosts());
        return "index";
    }
}
