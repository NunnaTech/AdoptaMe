package mx.com.adoptame.entities.character;

import lombok.extern.slf4j.Slf4j;
import mx.com.adoptame.entities.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/character")
@Slf4j
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/")
    public String type(Model model) {
        model.addAttribute("list", characterService.findAll());
        return "views/resources/character/characterList";
    }

    @GetMapping("/form")
    public String form(Model model, Character character) {
        return "views/resources/character/characterForm";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Character character, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "views/resources/character/characterForm";
            } else {
                character.setStatus(true);
                characterService.save(character);
                redirectAttributes.addFlashAttribute("msg_success", "Carácter guardado exitosamente");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "redirect:/character/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, Character character, RedirectAttributes redirectAttributes) {
        character = characterService.findOne(id).orElse(null);
        if (character == null) {
            redirectAttributes.addFlashAttribute("msg_error", "Elemento no encontrado");
            return "redirect:/character/";
        }
        model.addAttribute("character", character);
        return "views/resources/character/characterForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, Character character, RedirectAttributes redirectAttributes) {
        if (characterService.delete(id)) {
            redirectAttributes.addFlashAttribute("msg_success", "Carácter eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Carácter no eliminado");
        }
        return "redirect:/character/";
    }

}
