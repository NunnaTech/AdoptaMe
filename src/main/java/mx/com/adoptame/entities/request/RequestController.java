package mx.com.adoptame.entities.request;

import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @PostMapping("/login")
    private String login() {
        return "views/authentication/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model, User user) {
        return "views/authentication/forgotPassword";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Request request, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(request);
            if (bindingResult.hasErrors()) {
                return "views/authentication/login";
            } else {
                Optional<User> user = Optional.of(new User());
                userService.save(request.getUser());
                Optional<Profile> profile;
                if (user.isPresent()) {
                    profile = profileService.save(request.getUser().getProfile());
                    request.setUser(user.get());
                    request.getUser().setProfile(profile.get());
                    requestService.save(request);
                    redirectAttributes.addFlashAttribute("msg_success", "Usuario registrado exitosamente");
                } else {
                    redirectAttributes.addFlashAttribute("msg_error", "Usuario no registrado exitosamente");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/login";
    }
}
