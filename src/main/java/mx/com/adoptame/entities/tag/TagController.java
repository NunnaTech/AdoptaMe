package mx.com.adoptame.entities.tag;

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
@RequestMapping("/tag")
@Slf4j
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", tagService.findAll());
        return "views/resources/tag/tagList";
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Tag tag) {
        return "views/resources/tag/tagForm";
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/resources/tag/tagForm";
            } else {
                tagService.save(tag);
                redirectAttributes.addFlashAttribute("msg_success", "Etiqueta guardada exitosamente");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/tag/";
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, Tag tag, RedirectAttributes redirectAttributes) {
        tag = tagService.findOne(id).orElse(null);
        if (tag == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Etiqueta no encontrada");
            return "redirect:/tag/";
        }
        model.addAttribute("tag", tag);
        return "views/resources/tag/tagForm";
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, Model model, Tag tag, RedirectAttributes redirectAttributes) {
        if (tagService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Etiqueta eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Etiqueta no eliminado");
        }
        return "redirect:/tag/";
    }
}
