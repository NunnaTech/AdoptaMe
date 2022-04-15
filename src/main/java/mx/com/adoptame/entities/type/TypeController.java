package mx.com.adoptame.entities.type;

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
@RequestMapping("/type")
public class TypeController {

    private static final String TYPE = "redirect:/type/";
    private static final String TYPEFORM = "views/resources/type/typeForm";

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(TypeController.class);

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", typeService.findAll());
        return "views/resources/type/typeList";
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Type type) {
        return TYPEFORM;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Authentication authentication, @Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return TYPEFORM;
            } else {
                String username = authentication.getName();
                Optional<User> user = userService.findByEmail(username);
                if (user.isPresent()) {
                    type.setStatus(true);
                    typeService.save(type, user.get());
                    redirectAttributes.addFlashAttribute("msg_success", "Tipo guardada exitosamente");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return TYPE;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var type = typeService.findOne(id).orElse(null);
        if (type == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Tipo no encontrado");
            return TYPE;
        }
        model.addAttribute("type", type);
        return TYPEFORM;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(typeService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute("msg_success", "Tipo eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Tipo no eliminado");
            }
        }
        return TYPE;
    }
}
