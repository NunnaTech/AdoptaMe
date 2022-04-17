package mx.com.adoptame.entities.pet.controllers;

import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.entities.PetImage;
import mx.com.adoptame.entities.pet.services.PetImageService;
import mx.com.adoptame.entities.pet.services.PetService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/petsImages")
public class PetImageController {

    private static final String SMSERROR = "msg_error";

    @Autowired
    private PetService petService;

    @Autowired
    private PetImageService petImageService;

    private Logger logger = LoggerFactory.getLogger(PetImageController.class);

    @GetMapping("/images/{id}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String images(@PathVariable("id") Integer id, Model model, PetImage petImage, RedirectAttributes redirectAttributes) {
        var pet = petService.findOne(id).orElse(null);
        if (pet == null) {
            redirectAttributes.addFlashAttribute(SMSERROR, "Mascota no encontrado");
            return "redirect:/pets/admin";
        }
        model.addAttribute("list", pet);
        return "views/pets/petsImagesList";
    }

    @PostMapping("/images/save")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String save(@RequestParam("idPet") Integer idPet, @RequestParam("image") String image, RedirectAttributes redirectAttributes) {
        try {
            var petImage = new PetImage();
            var pet = new Pet();
            pet.setId(idPet);
            petImage.setPet(pet);
            petImage.setImage(image);
            petImageService.save(petImage);
            redirectAttributes.addFlashAttribute("msg_success", "Imagen guardada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(SMSERROR, "Imagen no guardada");
            logger.error(e.getMessage());
        }
        return "redirect:/petsImages/images/" + idPet;
    }

    @GetMapping("/delete/{idPet}/{idImage}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String delete(@PathVariable("idPet") Integer idPet, @PathVariable("idImage") Integer idImage, RedirectAttributes redirectAttributes) {
        if (Boolean.TRUE.equals(petImageService.delete(idImage))) {
            redirectAttributes.addFlashAttribute("msg_success", "Imagen eliminada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute(SMSERROR, "Imagen no eliminado");
        }
        return "redirect:/petsImages/images/" + idPet;
    }

}
