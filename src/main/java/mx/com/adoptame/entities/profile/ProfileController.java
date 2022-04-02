package mx.com.adoptame.entities.profile;

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

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired private UserService userService;

    @GetMapping("/")
    public String type(Model model, Profile profile) {
        try {
            profile = profileService.findOne(1).get();
            model.addAttribute("profile", profile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            System.err.println(e.getMessage());
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
//         TODO  implementar el cambio de contraseñas
        try{
            Optional<User> user = userService.findOne(id);
            if(user.isPresent()){
                if(userService.updatePassword(user.get(),currentPassword,newPassword,repeatPassword)){
                    // TODO si es exitoso el cambio, tiene que salir la sesión y deberá volver a iniciar sesión
                    return "redirect:/request/login";
                }else{
                    redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al actualizar la contraseña, intente nuevamente");
                    return "redirect:/profile/";
                }
            }else{
                redirectAttributes.addFlashAttribute("msg_error", "Elemento no encontrado");
                return "redirect:/profile/";
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return "redirect:/profile/";
    }

}
