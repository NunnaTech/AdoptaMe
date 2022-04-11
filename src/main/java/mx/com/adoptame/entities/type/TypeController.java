package mx.com.adoptame.entities.type;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/type")
@Slf4j
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/")
    public String type(Model model) {
        model.addAttribute("list", typeService.findAll());
        return "views/resources/type/typeList";
    }

    @GetMapping("/form")
    public String form(Model model, Type type) {
        return "views/resources/type/typeForm";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/resources/type/typeForm";
            } else {
                type.setStatus(true);
                typeService.save(type);
                redirectAttributes.addFlashAttribute("msg_success", "Tipo guardada exitosamente");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/type/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, Type type, RedirectAttributes redirectAttributes) {
        type = typeService.findOne(id).orElse(null);
        if (type == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Tipo no encontrado");
            return "redirect:/type/";
        }
        model.addAttribute("type", type);
        return "views/resources/type/typeForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, Type type, RedirectAttributes redirectAttributes) {
        if (typeService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Tipo eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Tipo no eliminado");
        }
        return "redirect:/type/";
    }
}
