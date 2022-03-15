package mx.com.adoptame.entities.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/color/")
public class ColorController {
    @Autowired
    private ColorService service;

}
