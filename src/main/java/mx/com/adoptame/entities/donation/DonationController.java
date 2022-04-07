package mx.com.adoptame.entities.donation;

import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    private Logger logger = LoggerFactory.getLogger(DonationController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String donation(Model model) {
        model.addAttribute("list", donationService.findAll());
        return "views/donations";
    }

    @GetMapping("/")
    public String owns(Model model, Authentication authentication) {
       try{
           String username = authentication.getName();
           Optional<User> user = userService.findByEmail(username);
            if(user.isPresent()){
                model.addAttribute("list", user.get().getDonations());
            }else{
                model.addAttribute("list", new ArrayList<>());
            }
       }catch (Exception e){
           model.addAttribute("list", new ArrayList<>());
           logger.error(e.getMessage());
       }
        return "views/donations";
    }

}
