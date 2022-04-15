package mx.com.adoptame.entities.news;

import mx.com.adoptame.entities.tag.TagService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class NewsController {

    private static final String NEWS = "redirect:/blog/";
    private static final String NEWSADMIN = "redirect:/blog/admin";
    private static final String NEWSFORM = "views/blog/blogForm";
    private static final String NEWSLIST = "views/blog/blogList";
    private static final String NEWSNOTFOUND = "Blog no encontrado";
    private static final String SMSERROR = "msg_error";

    @Autowired
    private NewsService newsService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(NewsController.class);

    @GetMapping("")
    public String home(Model model, News news) {
        model.addAttribute("newsList", newsService.findAllActives());
        model.addAttribute("newsTop", newsService.findLastFive());
        return "views/blog/blogs";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<News> news = newsService.findOne(id);
            if (news.isPresent() && Boolean.TRUE.equals(news.get().getIsMain())) {
                model.addAttribute("news", news.get());
                return "views/blog/blog";
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, NEWSNOTFOUND);
                return NEWS;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(SMSERROR, NEWSNOTFOUND);
            logger.error(e.getMessage());
            return NEWS;
        }

    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMINISTRATOR")
    public String management(Model model, News news) {
        model.addAttribute("list", newsService.findAll());
        return NEWSLIST;
    }

    @GetMapping("/admin/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, News news) {
        model.addAttribute("news", news);
        model.addAttribute("tags", tagService.findAll());
        return NEWSFORM;
    }

    @GetMapping("/admin/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var news = newsService.findOne(id).orElse(null);
        if (news == null) {
            redirectAttributes.addFlashAttribute(SMSERROR, NEWSNOTFOUND);
            return NEWSADMIN;
        }
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("news", news);
        return NEWSFORM;
    }

    @GetMapping("/admin/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(newsService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute("msg_success", "Blog eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, "Blog no eliminado");
            }
        }
        return NEWSADMIN;
    }

    @PostMapping("/admin/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid News news, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("tagsList", tagService.findAll());
                return NEWSFORM;
            } else {
                String username = authentication.getName();
                Optional<User> user = userService.findByEmail(username);
                if (user.isPresent()) {
                    news.setUser(user.get());
                    newsService.save(news, user.get());
                    redirectAttributes.addFlashAttribute("msg_success", "Blog guardado exitosamente");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return NEWSADMIN;
    }
}
