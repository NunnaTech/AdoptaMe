package mx.com.adoptame.util;

import mx.com.adoptame.entities.character.CharacterService;
import mx.com.adoptame.entities.color.ColorService;
import mx.com.adoptame.entities.role.RoleService;
import mx.com.adoptame.entities.size.SizeService;
import mx.com.adoptame.entities.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialDataBase implements CommandLineRunner {
    @Autowired
    private ColorService colorService;
    @Autowired
    private CharacterService characterService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TagService tagService;

    @Autowired
    private SizeService sizeService;
    @Override
    public void run(String... args) throws Exception {
        // We are adding all data necessary for the correct workflow of the application
        colorService.fillInitialData();
        characterService.fillInitialData();
        roleService.fillInitialData();
        sizeService.fillInitialData();
        tagService.fillInitialData();
    }
}
