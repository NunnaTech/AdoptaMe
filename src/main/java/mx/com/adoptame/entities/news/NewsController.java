package mx.com.adoptame.entities.news;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/blog/")
@Slf4j
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @GetMapping(path = {""})
    public String home(Model model, News news) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("newsList", newsService.findAll());
        return "views/blog/blogs";
    }

    @GetMapping(path = {"admin"})
    public String management(Model model, News news) {
        model.addAttribute("navbar", "navbar-admin");
        return "views/blog/blog-management";
    }

    @GetMapping(path = {"save"})
    public String save(Model model, News news) {
        model.addAttribute("navbar", "navbar-admin");
        return "views/blog/blog-create";
    }

    @PostMapping("save")
    public String save(Model model, @Valid News news, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        news.setImage("adsadasd");
        news.setUser(userService.findOne(2).get());
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("navbar", "navbar-admin");

                return "views/blog/blog-create";
            } else {
                newsService.save(news);
                redirectAttributes.addFlashAttribute("msg_success", "Blog guardado exitosamente");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/blog/admin";
    }
}
