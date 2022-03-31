package mx.com.adoptame.controller;

import mx.com.adoptame.entities.news.NewsService;
import mx.com.adoptame.entities.request.Request;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;

@Controller()
public class HomeController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("fix", "fix");
        model.addAttribute("mainNews", newsService.findMainNews());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Request request) {
        model.addAttribute("request", request);
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("fix", "fix");
        return "views/authentication/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model, User user) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("fix", "fix");
        return "views/authentication/forgotPassword";
    }



    @GetMapping("/dashboard")
    public String dashboard(Model model){
        return "views/dashboard/dashboard-admin";
    }
}
