package mx.com.adoptame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("fix", "fix");
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        return "views/dashboard/dashboard-admin";
    }

    @GetMapping("/noscript")
    public String noscript(){
        return "views/errorpages/error405";
    }


    @GetMapping("/test")
    public String test(){
        return "views/pets/pets";
    }

}
