package mx.com.adoptame.entities.pet.controllers;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.entities.PetImage;
import mx.com.adoptame.entities.pet.services.PetImageService;
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

    @Autowired
    private PetImageService petImageService;

    @GetMapping("/images/{id}")
    public String images(@PathVariable("id") Integer id, Model model, PetImage petImage, RedirectAttributes redirectAttributes) {
        Pet pet = petService.findOne(id).orElse(null);
        if (pet == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Elemento no encontrado");
            return "redirect:/pets/admin";
        }
        model.addAttribute("list", pet);
        return "views/pets/petsImagesList";
    }


    @PostMapping("/images/save")
    public String save(@RequestParam("idPet") Integer idPet, @RequestParam("image") String image, RedirectAttributes redirectAttributes) {
        try {
            PetImage petImage = new PetImage();
            Pet pet = new Pet();
            pet.setId(idPet);
            petImage.setPet(pet);
            petImage.setImage(image);
            petImageService.save(petImage);
            redirectAttributes.addFlashAttribute("msg_success", "Imagen guardada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg_error", "Imagen no guardada");
            System.err.println(e.getMessage());
        }
        return "redirect:/petsImages/images/" + idPet;
    }

    @GetMapping("/delete/{idPet}/{idImage}")
    public String delete(@PathVariable("idPet") Integer idPet, @PathVariable("idImage") Integer idImage, RedirectAttributes redirectAttributes) {
        if (petImageService.delete(idImage)) {
            redirectAttributes.addFlashAttribute("msg_success", "Imagen eliminada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Imagen no eliminado");
        }
        return "redirect:/petsImages/images/" + idPet;
    }

}
