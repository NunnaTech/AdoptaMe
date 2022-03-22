package mx.com.adoptame.entities.user;

import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileService;
import mx.com.adoptame.entities.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String type(Model model) {
        model.addAttribute("list", profileService.findAll());
        return "views/user/userList";
    }

    @GetMapping("/form")
    public String form(Model model, Profile profile) {
        model.addAttribute("listRoles",roleService.findAll());
        return "views/user/userForm";
    }


    @GetMapping("/request")
    public String request(Model model){
        model.addAttribute("list", profileService.findAll());
        return "views/user/userRequest";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, Profile profile, RedirectAttributes redirectAttributes) {
        profile = profileService.findOne(id).orElse(null);
        if (profile == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Elemento no encontrado");
            return "redirect:/user/";
        }
        model.addAttribute("listRoles",roleService.findAll());
        model.addAttribute("profile", profile);
        return "views/user/userForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, Profile profile, RedirectAttributes redirectAttributes) {
        if (profileService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Usuario eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Usuario no eliminado");
        }
        return "redirect:/user/";
    }


    @PostMapping("/save")
    public String save(Model model, @Valid Profile profile, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/user/userForm";
            } else {
                profileService.save(profile);
                redirectAttributes.addFlashAttribute("msg_success", "Usuario guardada exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/user/";
    }


    @PostMapping("/changePassword")
    private String changePassword(){
        return "/";
    }
}
