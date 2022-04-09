package mx.com.adoptame.controller;

import mx.com.adoptame.entities.donation.DonationService;
import mx.com.adoptame.entities.news.NewsService;
import mx.com.adoptame.entities.pet.services.PetAdoptedService;
import mx.com.adoptame.entities.pet.services.PetService;
import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.request.RequestService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller()
public class HomeController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private UserService userService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private PetService petService;
    @Autowired
    private PetAdoptedService petAdoptedService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("navbar", "navbar-all");
        model.addAttribute("fix", "fix");
        model.addAttribute("mainNews", newsService.findMainNews());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Profile profile, RedirectAttributes redirectAttributes) {
        model.addAttribute("profile", profile);
        return "views/authentication/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication, HttpSession httpSession) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        user.ifPresent(value -> {
            value.setPassword(null);
            httpSession.setAttribute("user", value);
        });
        if (user.isPresent()) {
            boolean isAdopted = userService.isAdopter(username);
            if (isAdopted) {
                model.addAttribute("myPetsFavorites", user.get().getFavoitesPets().size());
                model.addAttribute("myAcceptedRequests", petAdoptedService.countByUserRequestAccepted(user.get().getId()));
                model.addAttribute("myPendingRequests", petAdoptedService.countByUserRequestPending(user.get().getId()));
                model.addAttribute("myCancelledRequests", petAdoptedService.countByUserRequestCanceled(user.get().getId()));
                model.addAttribute("myDonationQuantity", donationService.sumCuantitybyUserId(user.get().getId()));
            } else {
                model.addAttribute("petsCount", petService.countTotal());
                model.addAttribute("petsActive", petService.coutnByIsActive(true));
                model.addAttribute("petsDeactivate", petService.coutnByIsActive(false));
                model.addAttribute("petsAdopted", petService.coutnByIsAdopted(true));
                model.addAttribute("petsTop", petService.findTopFive());
                model.addAttribute("usersCount", userService.countTotal());
                model.addAttribute("usersVolunteer", userService.countVolunteer());
                model.addAttribute("usersAdopter", userService.countAdopter());
                model.addAttribute("usersRequest", requestService.findPending());
                model.addAttribute("blogCount", newsService.countNews());
                model.addAttribute("blogMain", newsService.countMainNews());
                model.addAttribute("blogPublished", newsService.countPublishedNews());
                model.addAttribute("donationCuantity", donationService.sumCuantity());
                model.addAttribute("donationTop5", donationService.findTop5());
            }
        }
        return "views/dashboard";
    }

    @GetMapping("/noscript")
    public String noscript() {
        return "views/errorpages/noscript";
    }
}
