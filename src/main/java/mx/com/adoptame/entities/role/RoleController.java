package mx.com.adoptame.entities.role;

import org.slf4j.LoggerFactory;
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
import org.slf4j.Logger;

@Controller
@RequestMapping("/role")
public class RoleController {

    private static final String ROLE = "redirect:/role/";
    private static final String ROLEFORM = "views/resources/role/roleForm";

    @Autowired
    private RoleService roleService;

    private Logger logger =LoggerFactory.getLogger(RoleController.class);

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", roleService.findAll());
        return "views/resources/role/roleList";
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Role role) {
        return ROLEFORM;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return ROLEFORM;
            } else {
                roleService.save(role);
                redirectAttributes.addFlashAttribute("msg_success", "Rol guardado exitosamente");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ROLE;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var role = roleService.findOne(id).orElse(null);
        if (role == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Rol no encontrado");
            return ROLE;
        }
        model.addAttribute("role", role);
        return ROLEFORM;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, Model model, Role role, RedirectAttributes redirectAttributes) {
        if (Boolean.TRUE.equals(roleService.delete(id))) {
            redirectAttributes.addFlashAttribute("msg_success", "Rol eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Rol no eliminado");
        }
        return ROLE;
    }

}
