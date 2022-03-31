package mx.com.adoptame.entities.user;

import mx.com.adoptame.config.email.EmailService;
import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileService;
import mx.com.adoptame.entities.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ServletContext context;
    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String type(Model model) {
        model.addAttribute("list", profileService.findAll());
        return "views/user/userList";
    }

    @GetMapping("/form")
    public String form(Model model, Profile profile) {
        model.addAttribute("listRoles", roleService.findAll());
        return "views/user/userForm";
    }


    @GetMapping("/request")
    public String request(Model model) {
        model.addAttribute("list", profileService.findAll());
        return "views/user/userRequest";
    }


    @GetMapping("/acept/{id}")
    public String acept(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        // TODO metodo para implemantar y aceptar al usuario
        System.err.println(id);
        return "redirect:/user/request/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, Profile profile, RedirectAttributes redirectAttributes) {
        profile = profileService.findOne(id).orElse(null);
        if (profile == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Elemento no encontrado");
            return "redirect:/user/";
        }
        model.addAttribute("listRoles", roleService.findAll());
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
    @PostMapping("/forgotPassword")
    public String sendEmail(@RequestParam String email){
        String path = context.getContextPath();
        userService.sedEmail(email, path);
        return "redirect:/login";
    }

    @GetMapping("/link_restore_password")
    public String restorePassword(@Param(value = "token") String token, Model model, RedirectAttributes redirectAttributes){
        Optional<User> user = userService.findByLinkRestorePassword(token);
        if (!user.isPresent()) {
            redirectAttributes.addFlashAttribute("msg_success", "Usuario guardada exitosamente");
            return "redirect:/login"; //404 template
        }
        model.addAttribute("token", token);
        return "views/authentication/resetPassword";
    }

    @PostMapping("/reset_password_submit")
    public String processResetPassword(HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String repeatedPassword = request.getParameter("repeatedPassword");

        Boolean completed = userService.updatePassword(token, newPassword, repeatedPassword);

        if (!completed) {
            redirectAttributes.addFlashAttribute("msg_warning", "Token no valida");

            return "views/authentication/resetPassword";
        }
        redirectAttributes.addFlashAttribute("msg_success", "cambio de contraseña exitoso");

        return "redirect:/login";
    }
}
