package mx.com.adoptame.entities.pet.controllers;

import mx.com.adoptame.entities.pet.services.PetAdoptedService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/petsAdopted")
public class PetAdoptedController {

    @Autowired
    private PetAdoptedService petAdoptedService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String pets(Model model) {
        model.addAttribute("list", petAdoptedService.findAll());
        return "views/pets/petsAdopted";
    }

    @GetMapping("/acept/{id}/{idPet}/{idUser}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String acept(@PathVariable("id") Integer id, @PathVariable("idPet") Integer idPet, @PathVariable("idUser") Integer idUser, RedirectAttributes redirectAttributes) {
        if (Boolean.TRUE.equals(petAdoptedService.accept(id,idPet, idUser))) {
            redirectAttributes.addFlashAttribute("msg_success", "Adopción aceptada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Adopción no aceptada");
        }

        return "redirect:/petsAdopted/";
    }

    @GetMapping("/cancel/{id}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String calcel(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (Boolean.TRUE.equals(petAdoptedService.cancel(id))) {
            redirectAttributes.addFlashAttribute("msg_success", "Adopción rechazada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Adopción no rechazada");
        }
        return "redirect:/petsAdopted/";
    }
}
