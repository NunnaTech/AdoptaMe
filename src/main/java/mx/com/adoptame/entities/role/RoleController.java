package mx.com.adoptame.entities.role;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.type.Type;
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
@RequestMapping("/role")
@Slf4j
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String type(Model model) {
        model.addAttribute("list", roleService.findAll());
        return "views/resources/role/roleList";
    }

    @GetMapping("/form")
    public String form(Model model, Role role) {
        return "views/resources/role/roleForm";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/resources/role/roleForm";
            } else {
                roleService.save(role);
                redirectAttributes.addFlashAttribute("msg_success", "Rol guardado exitosamente");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/role/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, Role role, RedirectAttributes redirectAttributes) {
        role = roleService.findOne(id).orElse(null);
        if (role == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Elemento no encontrado");
            return "redirect:/role/";
        }
        model.addAttribute("role", role);
        return "views/resources/role/roleForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, Role role, RedirectAttributes redirectAttributes) {
        if (roleService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Rol eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Rol no eliminado");
        }
        return "redirect:/role/";
    }
    
}
