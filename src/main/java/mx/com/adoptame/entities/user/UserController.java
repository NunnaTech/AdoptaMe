package mx.com.adoptame.entities.user;

import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileService;
import mx.com.adoptame.entities.request.RequestService;
import mx.com.adoptame.entities.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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

    private static final String USER = "redirect:/user/";
    private static final String LOGIN = "redirect:/login";
    private static final String USERFORM = "views/user/userForm";
    private static final String SMSERROR = "msg_error";
    private static final String SMSSUCCESS = "msg_success";

    @Autowired
    private RequestService requestService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

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
        return USERFORM;
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
        var request = requestService.accept(id);
        if (request != null) {
            redirectAttributes.addFlashAttribute(SMSSUCCESS, "Usuario aceptado exitosamente");
            userService.sendActivateEmail(request.getUser());
        } else {
            redirectAttributes.addFlashAttribute(SMSERROR, "Usuario no aceptado");
        }
        return "redirect:/user/request/";
    }

    @PostMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (Boolean.TRUE.equals(requestService.delete(id))) {
            redirectAttributes.addFlashAttribute(SMSSUCCESS, "Solicitud borrada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute(SMSERROR, "Usuario no aceptado");
        }
        return "redirect:/user/request/";
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var profile = profileService.findOne(id).orElse(null);
        if (profile == null) {
            redirectAttributes.addFlashAttribute(SMSERROR, "Usuario no encontrado");
            return USER;
        }
        model.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("profile", profile);
        return USERFORM;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(profileService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute(SMSSUCCESS, "Usuario eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, "Usuario no eliminado");
            }
        }
        return USER;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMINISTRATOR")
    public String save(Model model, @Valid Profile profile, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            profile.getUser().setPassword(profileService.recoveryPassword(profile));
            if (bindingResult.hasErrors()) {
                return USERFORM;
            }
            if (Boolean.TRUE.equals(userService.emailExist(profile.getUser().getUsername())) && profile.getId() == null) {
                redirectAttributes.addFlashAttribute(SMSERROR, "Ese correo ya existe");
                return USER;
            }
            if (profile.getUser().getRoles() == null) {
                redirectAttributes.addFlashAttribute(SMSERROR, "Debes de asignar un rol al usuario");
                return USER;
            }
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            user.ifPresent(value -> profileService.save(profile, value));
            redirectAttributes.addFlashAttribute(SMSSUCCESS, "Usuario guardado exitosamente");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return USERFORM;
        }
        return USER;
    }

    @PostMapping("/forgotPassword")
    public String sendEmail(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            if (Boolean.TRUE.equals(userService.sendForgotPasswordEmail(email))) {
                redirectAttributes.addFlashAttribute(SMSSUCCESS, "Correo enviado, revisa tu bandeja de entrada");
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, "Correo inexistente");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute(SMSSUCCESS, "Error con el servidor de correos");
        }
        return LOGIN;
    }

    @GetMapping("/link_restore_password")
    public String restorePassword(@Param(value = "token") String token, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findByLinkRestorePassword(token);
        if (!user.isPresent()) {
            redirectAttributes.addFlashAttribute(SMSERROR, "Código no valido");
            return LOGIN;
        }
        model.addAttribute("token", token);
        return "views/authentication/resetPassword";
    }

    @GetMapping("/activate")
    public String activate(@Param(value = "token") String token, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findByLinkActivateUsername(token);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute(SMSERROR, "Código no valido");
        } else {
            redirectAttributes.addFlashAttribute(SMSSUCCESS, "Correo confirmado");
            userService.activateUser(user.get());
        }
        return LOGIN;
    }

    @PostMapping("/reset_password_submit")
    public String processResetPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String repeatedPassword = request.getParameter("repeatedPassword");
        Boolean completed = userService.updatePassword(token, newPassword, repeatedPassword);
        if (Boolean.FALSE.equals(completed)) {
            redirectAttributes.addFlashAttribute("msg_warning", "Token no valida");
            return "views/authentication/resetPassword";
        }
        redirectAttributes.addFlashAttribute(SMSSUCCESS, "Cambio de contraseña exitoso");
        return LOGIN;
    }
}
