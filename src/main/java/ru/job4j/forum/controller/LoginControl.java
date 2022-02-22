package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.SecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginControl {
    private final SecurityService service;

    public LoginControl(SecurityService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @PostMapping("/login")
    public String loginCheck(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             Model model,
                             HttpServletRequest request) {
        User user = service.findUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            model.addAttribute("user", user);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/index";
        }
        return "redirect:/login?error=true";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login?logout=true";
    }
}
