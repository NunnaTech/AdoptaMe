package mx.com.adoptame.entities.request;

import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileService;
import mx.com.adoptame.entities.role.Role;
import mx.com.adoptame.entities.role.RoleService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model, User user) {
        return "views/authentication/forgotPassword";
    }

    @PostMapping("/save")
    public String save(Model model,

                       @RequestParam("role") String role,
                       @RequestParam("reason") String reason,
                       @Valid Profile profile,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/authentication/login";
            }
            if(userService.findByEmail(profile.getUser().getUsername()).isPresent()){
                redirectAttributes.addFlashAttribute("msg_error", "Intenta con otro correo electrónico");
                return "redirect:/login";
            }
            boolean success = false;
            if(role.equalsIgnoreCase("Voluntario")){
                profile.getUser().addRole();
                profile.getUser().setEnabled(false);
                String phone = profile.getPhone().replaceAll("[\\s]", "").replaceAll("\\(", "").replaceAll("\\)", "")
                        .replaceAll("-", "");
                profile.setPhone(phone);

                Optional<User> optionalUser = userService.addUser(profile.getUser());
                if (optionalUser.isPresent()){
                    Optional<Role> volunteer = roleService.findByType("ROLE_VOLUNTEER");
                    volunteer.ifPresent(value -> userService.addRole(optionalUser.get(), value));
                    profile.setUser(optionalUser.get());
                    profileService.addProfile(profile);
                    requestService.addRequest(reason, optionalUser.get());
                    success = true;
                }
            }else {
                profile.getUser().addRole();
                profile.getUser().setEnabled(true);
                Optional<User> optionalUser = userService.addUser(profile.getUser());
                if (optionalUser.isPresent()){
                    Optional<Role> volunteer = roleService.findByType("ROLE_ADOPTER");
                    volunteer.ifPresent(value -> userService.addRole(optionalUser.get(), value));
                    profile.setUser(optionalUser.get());
                    profileService.addProfile(profile);
                    success = true;
                }
            }

            if(success){
                redirectAttributes.addFlashAttribute("msg_success", "Usuario registrado exitosamente");
            }else{
                redirectAttributes.addFlashAttribute("msg_error", "Usuario no registrado exitosamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }
}
