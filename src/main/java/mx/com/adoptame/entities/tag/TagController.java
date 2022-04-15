package mx.com.adoptame.entities.tag;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/tag")
public class TagController {

    private static final String TAG = "redirect:/tag/";
    private static final String TAGFORM = "views/resources/tag/tagForm";

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", tagService.findAll());
        return "views/resources/tag/tagList";
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Tag tag) {
        return TAGFORM;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Authentication authentication, @Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return TAGFORM;
            } else {
                String username = authentication.getName();
                Optional<User> user = userService.findByEmail(username);
                if (user.isPresent()) {
                    tagService.save(tag, user.get());
                    redirectAttributes.addFlashAttribute("msg_success", "Etiqueta guardada exitosamente");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return TAG;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var tag = tagService.findOne(id).orElse(null);
        if (tag == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Etiqueta no encontrada");
            return TAG;
        }
        model.addAttribute("tag", tag);
        return TAGFORM;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(tagService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute("msg_success", "Etiqueta eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Etiqueta no eliminado");
            }
        }
        return TAG;
    }
}
