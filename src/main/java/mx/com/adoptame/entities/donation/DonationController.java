package mx.com.adoptame.entities.donation;

import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String donation(Model model) {
        model.addAttribute("list", donationService.findAll());
        return "views/donations";
    }

    @GetMapping("/")
    public String owns(Model model) {
        // TODO obtener el id de la sesión para obtener las donaciones de la persona
        Optional<User> user = userService.findOne(2);
        model.addAttribute("list", user.get().getDonations());
        return "views/donations";
    }

}
