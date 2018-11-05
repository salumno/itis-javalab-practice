package ru.kpfu.itis.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping
    private String mainPage(@ModelAttribute("model")ModelMap model) {
        return "main";
    }

    @GetMapping("/login-page")
    private String loginPage(@ModelAttribute("model")ModelMap model) {
        return "login";
    }

    @GetMapping("/users-page")
    private String usersPage(@ModelAttribute("model")ModelMap model) {
        return "users";
    }
}
