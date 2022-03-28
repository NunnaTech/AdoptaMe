package mx.com.adoptame.controller;

import mx.com.adoptame.entities.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class HomeController {
    @Autowired
    private NewsService newsService;
    @GetMapping(path = {"/"})
    public String index(Model model) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("fix", "fix");
        model.addAttribute("mainNews", newsService.findMainNews());

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
