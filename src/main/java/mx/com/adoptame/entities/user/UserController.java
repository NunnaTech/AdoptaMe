package mx.com.adoptame.entities.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/changePassword")
    private String changePassword(){
        return "/";
    }



}
