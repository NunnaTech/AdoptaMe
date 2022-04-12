package mx.com.adoptame.entities.user;

import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileService;
import mx.com.adoptame.entities.request.Request;
import mx.com.adoptame.entities.request.RequestService;
import mx.com.adoptame.entities.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private RequestService requestService;

    @Autowired private ProfileService profileService;

    @Autowired private RoleService roleService;

    @Autowired private UserService userService;

    @GetMapping("/")
    @Secured("ROLE_ADMINISTRATOR")
    public String type(Model model) {
        model.addAttribute("list", profileService.findAll());
        return "views/user/userList";
    }

    @GetMapping("/form")
    @Secured("ROLE_ADMINISTRATOR")
    public String form(Model model, Profile profile) {
        model.addAttribute("listRoles", roleService.findAll());
        return "views/user/userForm";
    }

    @GetMapping("/request")
    @Secured("ROLE_ADMINISTRATOR")
    public String request(Model model) {
        model.addAttribute("list", requestService.findAll());
        return "views/user/userRequest";
    }


    @PostMapping("/acept/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String acept(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Request  request =requestService.accept(id);
        if (request != null) {
            redirectAttributes.addFlashAttribute("msg_success", "Usuario aceptado exitosamente");
            userService.sendActivateEmail(request.getUser());
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Usuario no aceptado");
        }
        return "redirect:/user/request/";
    }

    @PostMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (requestService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Solicitud borrada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Usuario no aceptado");
        }
        return "redirect:/user/request/";
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, Profile profile, RedirectAttributes redirectAttributes) {
        profile = profileService.findOne(id).orElse(null);
        if (profile == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Usuario no encontrado");
            return "redirect:/user/";
        }
        model.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("profile", profile);
        return "views/user/userForm";
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, Model model, Profile profile, RedirectAttributes redirectAttributes) {
        if (profileService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Usuario eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Usuario no eliminado");
        }
        return "redirect:/user/";
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid Profile profile, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/user/userForm";
            } else {
                profileService.save(profile);
                redirectAttributes.addFlashAttribute("msg_success", "Usuario guardado exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/user/";
    }
    @PostMapping("/forgotPassword")
    public String sendEmail(@RequestParam String email){
        userService.sendForgotPasswordEmail(email);
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
    @GetMapping("/activate")
    public String activate(@Param(value = "token") String token, Model model, RedirectAttributes redirectAttributes){
        Optional<User> user = userService.findByLinkActivateUsername(token);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("msg_error", "Código no valido");
        }else {
            redirectAttributes.addFlashAttribute("msg_success", "Correo confirmado");
            userService.activateUser(user.get());
        }
        return "redirect:/login";
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
