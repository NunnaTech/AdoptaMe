package mx.com.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class HomeController {

    @GetMapping(path = {"/"})
    public String index(Model model) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("fix", "fix");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("navbar", "navbar-all");
        return "views/login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("navbar", "navbar-admin");
        return "views/dashboard/dashboard-admin";
    }
    @GetMapping("/voluntario")
    public String voluntario(Model model) {
        model.addAttribute("navbar", "navbar-voluntario");
        return "views/blank";
    }

    @GetMapping("/adoptador")
    public String adoptador(Model model) {
        model.addAttribute("navbar", "navbar-adoptador");
        return "views/blank";
    }

    @GetMapping("/mascotas")
    public String mascotas(Model model) {
        return "views/pets/pets";
    }

    @GetMapping("/mascotas-filter")
    public String mascotasFilter(Model model) {
        model.addAttribute("navbar", "navbar-all");
        return "views/pets/pets-filter";
    }

    @GetMapping("/blog")

    public String blog(Model model) {
        model.addAttribute("navbar", "navbar-all");
        return "views/blog/blog";
    }
}
