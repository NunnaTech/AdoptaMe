package mx.com.adoptame.entities.profile;

import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired private UserService userService;

    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping("/")
    public String type(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            if(user.isPresent()){
                model.addAttribute("profile", user.get().getProfile());
            }
        } catch (Exception e) {
           logger.error(e.getMessage());
        }
        return "views/profile/profileForm";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Profile profile, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            profile = profileService.findAndSetPerfil(profile);
            if (bindingResult.hasErrors()) {
                return "views/profile/profileForm";
            } else {
                profileService.save(profile);
                redirectAttributes.addFlashAttribute("msg_success", "Perfil guardado exitosamente");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al almacenar los datos, intente nuevamente");
        }
        return "redirect:/profile/";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("idUser") Integer id,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("repeatPassword") String repeatPassword,
            RedirectAttributes redirectAttributes
    ) {
        try{
            Optional<User> user = userService.findOne(id);
            if(user.isPresent()){
                if(userService.updatePassword(user.get(),currentPassword,newPassword,repeatPassword)){
                    redirectAttributes.addFlashAttribute("msg_success", "Contraseña cambiada correctamente");
                    return "redirect:/login";
                }else{
                    redirectAttributes.addFlashAttribute("msg_error", "La contraseña actual no es correcta");
                    return "redirect:/profile/";
                }
            }else{
                redirectAttributes.addFlashAttribute("msg_error", "Usuario no encontrado");
                return "redirect:/profile/";
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al actualizar la contraseña, intente nuevamente");
            logger.error(e.getMessage());
        }
        return "redirect:/profile/";
    }

}
