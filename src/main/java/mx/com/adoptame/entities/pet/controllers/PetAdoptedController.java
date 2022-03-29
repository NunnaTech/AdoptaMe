package mx.com.adoptame.entities.pet.controllers;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.pet.services.PetAdoptedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/petsAdopted")
@Slf4j
public class PetAdoptedController {

    @Autowired
    PetAdoptedService petAdoptedService;


    @GetMapping("/")
    public String pets(Model model) {
        model.addAttribute("list", petAdoptedService.findAll());
        return "views/pets/petsAdopted";
    }

    @GetMapping("/acept/{id}")
    public String acept(Model model, @PathVariable("id") Integer id) {
        // TODO implementar el aceptar una mascota adoptada
        return "views/pets/petsAdopted";
    }
}
