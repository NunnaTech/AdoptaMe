package mx.com.adoptame.entities.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    @Autowired
    private ProfileService profileService;

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
            if (bindingResult.hasErrors()) {
                return "views/profile/profileForm";
            } else {
                profileService.save(profile);
                redirectAttributes.addFlashAttribute("msg_success", "Tipo guardada exitosamente");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/profile/";
    }
}
