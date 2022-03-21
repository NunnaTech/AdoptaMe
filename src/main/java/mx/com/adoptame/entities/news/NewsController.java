package mx.com.adoptame.entities.news;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.tag.Tag;
import mx.com.adoptame.entities.tag.TagService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
@Slf4j
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    @GetMapping(path = {""})
    public String home(Model model, News news) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("newsList", newsService.findAll());
        return "views/blog/blogs";
    }

    @GetMapping(path = {"/admin"})
    public String management(Model model, News news) {
        model.addAttribute("navbar", "navbar-admin");
        model.addAttribute("topNews",newsService.findLastFive());
        return "views/blog/blog-management";
    }

    @GetMapping(path = {"/save"})
    public String save(Model model, News news) {
        model.addAttribute("navbar", "navbar-admin");
        model.addAttribute("tagsList", tagService.findAll());

        return "views/blog/blog-create";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid News news, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                       @ModelAttribute("tagValues") String tagValues) {
        news.setImage("adsadasd");
        news.setUser(userService.findOne(1).get());
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("navbar", "navbar-admin");
                model.addAttribute("tagsList", tagService.findAll());

                return "views/blog/blog-create";
            }
            news = newsService.save(news).get();
            String[] tags = tagValues.split(",");
            System.out.println(tags);
            for (String tag: tags) {
                Optional<Tag> tagItem = tagService.findOne(Integer.valueOf(tag));
                if (tagItem.isPresent()) {
                    newsService.saveTag(news, tagItem.get());
                }
            }
            redirectAttributes.addFlashAttribute("msg_success", "Blog guardado exitosamente");

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/blog/admin";
    }
}
