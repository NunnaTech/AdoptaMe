package mx.com.adoptame.entities.pet.controllers;

import mx.com.adoptame.entities.character.CharacterService;
import mx.com.adoptame.entities.color.ColorService;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.entities.PetAdopted;
import mx.com.adoptame.entities.pet.services.PetAdoptedService;
import mx.com.adoptame.entities.pet.services.PetService;
import mx.com.adoptame.entities.size.SizeService;
import mx.com.adoptame.entities.type.Type;
import mx.com.adoptame.entities.type.TypeService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pets")
public class PetController {

    private static final String PETFORM = "views/pets/petsForm";
    private static final String PETADMIN = "redirect:/pets/admin";
    private static final String PETNOTFOUND = "Mascota no encontrada";
    private static final String PETERRORFAVORITES = "Ocurrió un error al guardar en favoritos, intente nuevamente";
    private static final String SMSERROR = "msg_error";
    private static final String SMSSUCCESS = "msg_success";
    private static final String LISTCHARACTERS = "listCharacters";
    private static final String LISTCOLORS = "listColors";
    private static final String LISTSIZES = "listSizes";
    private static final String LISTTYPES = "listTypes";


    @Autowired
    private PetService petService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetAdoptedService petAdoptedService;

    private Logger logger = LoggerFactory.getLogger(PetController.class);

    @GetMapping("/")
    public String pets(Model model) {
        model.addAttribute("fix", "fix");
        model.addAttribute("list", petService.findLastThreePets());
        return "views/pets/pets";
    }

    @GetMapping("/{id}")
    public String pet(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Pet> pet = petService.findOne(id);
            if (pet.isPresent() && Boolean.TRUE.equals(pet.get().getIsActive())) {
                model.addAttribute("pet", pet.get());
                return "views/pets/pet";
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, PETNOTFOUND);
                return "redirect:/pets/";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(SMSERROR, PETNOTFOUND);
            logger.error(e.getMessage());
            return "redirect:/pets/";
        }
    }

    @GetMapping("/adoptions")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER", "ROLE_ADOPTER"})
    public String adoptions(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            if (user.isPresent()) {
                List<PetAdopted> petAdopted = petAdoptedService.findUsername(user.get().getId());
                model.addAttribute("list", petAdopted);
            } else {
                model.addAttribute("list", new ArrayList<>());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("list", new ArrayList<>());
        }
        return "views/pets/petsMyRequest";
    }

    @GetMapping("/favorites")
    public String favorites(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            if (user.isPresent()) {
                model.addAttribute("petList", user.get().getFavoitesPets());
            } else {
                model.addAttribute("list", new ArrayList<>());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("list", new ArrayList<>());
        }
        return "views/pets/petsFavorite";
    }

    @GetMapping("/adopted/{id}")
    public String adopted(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            Optional<Pet> pet = petService.findOne(id);
            if (pet.isPresent() && user.isPresent() && Boolean.TRUE.equals(pet.get().getIsActive())) {
                if (Boolean.TRUE.equals(petAdoptedService.checkIsPresentInAdoptions(pet.get(), user.get()))) {
                    redirectAttributes.addFlashAttribute(SMSERROR, "Esta mascota ya la solicitaste");
                } else {
                    var petAdopted = new PetAdopted(pet.get(), user.get());
                    petAdoptedService.save(petAdopted, user.get());
                    redirectAttributes.addFlashAttribute(SMSSUCCESS, "Solicitud de adopción realizada");
                }
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, "Ocurrió un error al realizar la solicitud de adopción, intente nuevamente");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(SMSERROR, "Necesitas iniciar sesión para realizar esta acción");
            return "redirect:/login";
        }
        return "redirect:/pets/filter";
    }

    @GetMapping("/like/{id}")
    public String like(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            Optional<Pet> pet = petService.findOne(id);
            if (pet.isPresent() && user.isPresent() && Boolean.TRUE.equals(pet.get().getIsActive())) {
                if (Boolean.TRUE.equals(petService.checkIsPresentInFavorites(pet.get(), user.get().getFavoitesPets()))) {
                    redirectAttributes.addFlashAttribute(SMSERROR, "Esta mascota ya esta guardada en favoritos");
                } else {
                    user.get().addToFavorite(pet.get());
                    userService.save(user.get());
                    redirectAttributes.addFlashAttribute(SMSSUCCESS, "Mascota guardada en favoritos");
                }
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, PETERRORFAVORITES);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(SMSERROR, "Necesitas iniciar sesión para realizar esta acción");
            return "redirect:/login";
        }
        return "redirect:/pets/filter";
    }

    @GetMapping("/dislike/{id}")
    public String dislike(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            Optional<Pet> pet = petService.findOne(id);
            if (pet.isPresent() && user.isPresent() && Boolean.TRUE.equals(pet.get().getIsActive())) {
                user.get().removeFromFavorite(pet.get());
                userService.save(user.get());
                redirectAttributes.addFlashAttribute(SMSSUCCESS, "Mascota removida de favoritos");
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, PETERRORFAVORITES);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(SMSERROR, PETERRORFAVORITES);
            logger.error(e.getMessage());
        }
        return "redirect:/pets/favorites";
    }

    @GetMapping("/filter")
    public String filter(Model model,
                         Optional<String> search,
                         Optional<String> types,
                         Optional<String> ages,
                         Optional<String> sizes,
                         Optional<String> characters,
                         Optional<String> colors) {
        List<Pet> characterList = petService.findPetsForAdopted();
        List<Type> typeList = typeService.findAll();

        if (search.isPresent()) {
            characterList = petService.findByNameOrBreed(search.get());
        }
        if (types.isPresent()) {
            characterList = petService.findByType(types.get());
        }

        if (ages.isPresent()) {
            characterList = petService.findByAge(ages.get());
        }

        if (sizes.isPresent()) {
            characterList = petService.findBySize(sizes.get());
        }

        if (characters.isPresent()) {
            characterList = petService.findByCharacters(characters.get());
        }
        if (colors.isPresent()) {
            characterList = petService.findByColor(colors.get());
        }
        model.addAttribute("typeList", typeList);
        model.addAttribute("sizeList", sizeService.findAll());
        model.addAttribute("characterList", characterService.findAll());
        model.addAttribute("colorList", colorService.findAll());
        model.addAttribute("petsList", characterList);
        return "views/pets/petsFilter";
    }

    @GetMapping("/admin")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String admin(Model model) {
        model.addAttribute("list", petService.findAll());
        return "views/pets/petsList";
    }

    @GetMapping("/admin/form")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String save(Model model, Pet pet) {
        model.addAttribute(LISTCHARACTERS, characterService.findAll());
        model.addAttribute(LISTCOLORS, colorService.findAll());
        model.addAttribute(LISTSIZES, sizeService.findAll());
        model.addAttribute(LISTTYPES, typeService.findAll());
        return PETFORM;
    }

    @GetMapping("/admin/request")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String request(Model model) {
        model.addAttribute("list", petService.findAllisActiveFalse());
        return "views/pets/petsRequest";
    }

    @GetMapping("/admin/acept/{id}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String acept(@PathVariable("id") Integer id, Model model, Pet pet, RedirectAttributes redirectAttributes) {
        if (Boolean.TRUE.equals(petService.accept(id))) {
            redirectAttributes.addFlashAttribute(SMSSUCCESS, "Mascota aceptada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute(SMSERROR, "Mascota no aceptada");
        }
        return "redirect:/pets/admin/request";
    }

    @GetMapping("/admin/edit/{id}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        var pet = petService.findOne(id).orElse(null);
        if (pet == null) {
            redirectAttributes.addFlashAttribute(SMSERROR, PETNOTFOUND);
            return PETADMIN;
        }
        model.addAttribute(LISTCHARACTERS, characterService.findAll());
        model.addAttribute(LISTCOLORS, colorService.findAll());
        model.addAttribute(LISTSIZES, sizeService.findAll());
        model.addAttribute(LISTTYPES, typeService.findAll());
        model.addAttribute("pet", pet);
        return PETFORM;
    }

    @PostMapping("/admin/save")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_VOLUNTEER"})
    public String save(Model model, @Valid Pet pet, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            String username = authentication.getName();
            boolean isAdmin = userService.isAdmin(username);
            pet.setIsActive(isAdmin || pet.getId() != null);
            if (bindingResult.hasErrors()) {
                model.addAttribute(LISTCHARACTERS, characterService.findAll());
                model.addAttribute(LISTCOLORS, colorService.findAll());
                model.addAttribute(LISTSIZES, sizeService.findAll());
                model.addAttribute(LISTTYPES, typeService.findAll());
                return PETFORM;
            } else {
                Optional<User> user = userService.findByEmail(username);
                pet.setIsDropped(false);
                pet.setIsAdopted(false);
                if (user.isPresent()) {
                    pet.setUser(user.get());
                    petService.save(pet, user.get());
                    redirectAttributes.addFlashAttribute(SMSSUCCESS, "Mascota guardado exitosamente");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return PETADMIN;
    }

    @GetMapping("/admin/delete/{id}")
    @Secured("ROLE_ADMINISTRATOR")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            if (Boolean.TRUE.equals(petService.delete(id, user.get()))) {
                redirectAttributes.addFlashAttribute(SMSSUCCESS, "Mascota eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute(SMSERROR, "Mascota no eliminado");
            }
        }
        return PETADMIN;
    }
}
