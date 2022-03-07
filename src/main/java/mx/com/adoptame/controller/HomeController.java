package mx.com.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class HomeController {

    @GetMapping(path = {"/"})
    public String index(Model model) {
        model.addAttribute("landing", true);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("landing", false);
        return "views/login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("landing", false);
        return "views/admin";
    }
}
