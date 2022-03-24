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

    @GetMapping("/mascotas")
    public String mascotas(Model model) {
        return "views/pets/pets";
    }

    @GetMapping("/mascotas-filter")
    public String mascotasFilter(Model model) {
        return "views/pets/petsFilter";
    }
}
