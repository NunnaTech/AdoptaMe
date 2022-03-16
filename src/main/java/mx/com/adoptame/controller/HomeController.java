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
        return "views/administrator/dashboard-admin";
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
    @GetMapping("/403")
    public String error403(Model model) {
        return "views/error403";
    }

    @GetMapping("/mascotas")
    public String mascotas(Model model) {
        return "views/mascotas";
    }




}
