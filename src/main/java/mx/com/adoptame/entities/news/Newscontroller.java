package mx.com.adoptame.entities.news;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog/")
public class Newscontroller {

    @GetMapping(path = {"/admin"})
    public String index(Model model) {
        model.addAttribute("navbar", "navbar-admin");
        return "views/blog/blog-management";
    }
}
