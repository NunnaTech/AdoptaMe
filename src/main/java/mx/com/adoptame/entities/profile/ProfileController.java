package mx.com.adoptame.entities.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/")
    public String type(Model model) {
        try {
//          model.addAttribute("user", profileService.findOne(1).get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "views/profile/profile";
    }

}
