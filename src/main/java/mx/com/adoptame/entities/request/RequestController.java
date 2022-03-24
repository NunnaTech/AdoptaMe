package mx.com.adoptame.entities.request;

import mx.com.adoptame.entities.role.RoleService;
import mx.com.adoptame.entities.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/login")
    private String login() {
        return "views/login";
    }

    @GetMapping("/login")
    public String login(Model model, Request request) {
        model.addAttribute("request", request);
        return "views/login";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Request request, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(request);
            if (bindingResult.hasErrors()) {
                return "views/login";
            } else {
                requestService.save(request);
                redirectAttributes.addFlashAttribute("msg_success", "Tipo guardada exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/request/login";
    }

}
