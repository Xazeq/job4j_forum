package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.SecurityService;

@Controller
public class RegControl {
    private final SecurityService service;

    public RegControl(SecurityService service) {
        this.service = service;
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        if (service.existsByUsername(user.getUsername())) {
            model.addAttribute("errorMessage", "Пользователь с таким username уже зарегистрирован");
            return regPage();
        }
        user.setEnabled(true);
        user.setAuthority(service.findByAuthority("ROLE_USER"));
        service.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}
