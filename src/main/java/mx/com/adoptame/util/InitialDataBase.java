package mx.com.adoptame.util;

import mx.com.adoptame.entities.character.CharacterService;
import mx.com.adoptame.entities.color.ColorService;
import mx.com.adoptame.entities.role.RoleService;
import mx.com.adoptame.entities.size.SizeService;
import mx.com.adoptame.entities.tag.TagService;
import mx.com.adoptame.entities.type.TypeService;
import mx.com.adoptame.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialDataBase implements CommandLineRunner {
    @Autowired private ColorService colorService;

    @Autowired private CharacterService characterService;

    @Autowired private RoleService roleService;

    @Autowired private TagService tagService;

    @Autowired private UserService userService;

    @Autowired private SizeService sizeService;

    @Autowired private TypeService typeService;

    @Override
    public void run(String... args) throws Exception {
        colorService.fillInitialData();
        characterService.fillInitialData();
        typeService.fillInitialData();
        roleService.fillInitialData();
        sizeService.fillInitialData();
        tagService.fillInitialData();
        userService.fillInitialData();
    }
}
