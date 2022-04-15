package mx.com.adoptame.entities.color;

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
@RequestMapping("/color")
public class ColorController {

    private static final String COLOR = "redirect:/color/";
    private static final String COLORFORM = "views/resources/color/colorForm";
    private static final String COLORLIST = "views/resources/color/colorList";

    @Autowired
    private ColorService colorService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(ColorController.class);

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", colorService.findAll());
        return COLORLIST;
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Color color) {
        return COLORFORM;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid Color color, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            if (bindingResult.hasErrors()) {
                return COLORFORM;
            } else {
                String username = authentication.getName();
                Optional<User> user = userService.findByEmail(username);
                if (user.isPresent()) {
                    color.setStatus(true);
                    colorService.save(color, user.get());
                    redirectAttributes.addFlashAttribute("msg_success", "Color guardado exitosamente");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return COLOR;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var color = colorService.findOne(id).orElse(null);
        if (color == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Color no encontrado");
            return COLOR;
        }
        model.addAttribute("color", color);
        return COLORFORM;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(colorService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute("msg_success", "Color eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Color no eliminado");
            }

        }
        return COLOR;
    }
}
