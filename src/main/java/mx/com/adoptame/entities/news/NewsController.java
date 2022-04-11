package mx.com.adoptame.entities.news;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.pet.controllers.PetController;
import mx.com.adoptame.entities.tag.Tag;
import mx.com.adoptame.entities.tag.TagService;
import mx.com.adoptame.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    private Logger logger = LoggerFactory.getLogger(NewsController.class);

    //    Para todos
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
           if (news.isPresent() && news.get().getIsMain()){
               model.addAttribute("news", news.get());
               return "views/blog/blog";
           }else{
               redirectAttributes.addFlashAttribute("msg_error", "Blog no encontrado");
               return "redirect:/blog/";
           }
       }catch (Exception e){
           redirectAttributes.addFlashAttribute("msg_error", "Blog no encontrado");
           logger.error(e.getMessage());
           return "redirect:/blog/";
       }

    }
    //    List admin
    @GetMapping("/admin")
    @Secured("ROLE_ADMINISTRATOR")
    public String management(Model model, News news) {
        model.addAttribute("list", newsService.findAll());
        return "views/blog/blogList";
    }

    //    Form admin
    @GetMapping("/admin/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, News news) {
        model.addAttribute("news", news);
        model.addAttribute("tags", tagService.findAll());
        return "views/blog/blogForm";
    }

    //    Edit blog
    @GetMapping("/admin/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, News news, RedirectAttributes redirectAttributes) {
        news = newsService.findOne(id).orElse(null);
        if (news == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Blog no encontrado");
            return "redirect:/blog/admin";
        }
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("news", news);
        return "views/blog/blogForm";

    }

    @GetMapping("/admin/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, Model model, News news, RedirectAttributes redirectAttributes) {
        if (newsService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Blog eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Blog no eliminado");
        }
        return "redirect:/blog/admin";
    }

    //    Save admin
    @PostMapping("/admin/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid News news, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        news.setUser(userService.findOne(1).get());
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("tagsList", tagService.findAll());
                return "views/blog/blogForm";
            }
            news = newsService.save(news).get();
            redirectAttributes.addFlashAttribute("msg_success", "Blog guardado exitosamente");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/blog/admin";
    }
}
