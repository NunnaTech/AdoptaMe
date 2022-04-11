package mx.com.adoptame.entities.color;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/color")
@Slf4j
public class ColorController {
    @Autowired
    private ColorService colorService;

    
    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", colorService.findAll());
        return "views/resources/color/colorList";
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Color color) {
        return "views/resources/color/colorForm";
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid Color color, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/resources/color/colorForm";
            } else {
                color.setStatus(true);
                colorService.save(color);
                redirectAttributes.addFlashAttribute("msg_success", "Color guardado exitosamente");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/color/";
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, Color color, RedirectAttributes redirectAttributes) {
        color = colorService.findOne(id).orElse(null);
        if (color == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Color no encontrado");
            return "redirect:/color/";
        }
        model.addAttribute("color", color);
        return "views/resources/color/colorForm";
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, Model model, Color color, RedirectAttributes redirectAttributes) {
        if (colorService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Color eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Color no eliminado");
        }
        return "redirect:/color/";
    }
}
