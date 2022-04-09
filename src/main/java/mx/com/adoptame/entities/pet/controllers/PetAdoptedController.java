package mx.com.adoptame.entities.pet.controllers;

import mx.com.adoptame.entities.pet.services.PetAdoptedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/petsAdopted")
public class PetAdoptedController {

    @Autowired private PetAdoptedService petAdoptedService;


    @GetMapping("/")
    public String pets(Model model) {
        model.addAttribute("list", petAdoptedService.findAll());
        return "views/pets/petsAdopted";
    }

    @GetMapping("/acept/{id}")
    public String acept(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (Boolean.TRUE.equals(petAdoptedService.accept(id))) {
            redirectAttributes.addFlashAttribute("msg_success", "Adopción aceptada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Adopción no aceptada");
        }
        return "redirect:/petsAdopted/";
    }
}
