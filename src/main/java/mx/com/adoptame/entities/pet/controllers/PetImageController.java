package mx.com.adoptame.entities.pet.controllers;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/petsImages")
@Slf4j
public class PetImageController {

    @Autowired
    private PetService petService;

    @GetMapping("/images/{id}")
    public String images(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Pet pet = petService.findOne(id).orElse(null);
        if (pet == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Elemento no encontrado");
            return "redirect:/pets/admin";
        }
        model.addAttribute("list", pet);
        return "views/pets/petsImagesList";
    }


    @PostMapping("/images/save")
    public String save(@RequestParam("image") String image, @RequestParam("id") Integer id) {
        // TODO guardar una imagen de la mascota
        return "redirect:/petsImages/images/" + id;
    }

}
