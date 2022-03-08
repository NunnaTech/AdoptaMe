package mx.com.adoptame.entities.color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    // public ColorService(){
    //     // The entity Color is Empty ?
    //     fillInicialData();   
    // }

    public List<Color> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    public Optional<Color> saveColor(Color color) {
        // TODO Auto-generated method stub
        return null;
    }
    public void fillInicialData() {
        if(colorRepository.count() > 0) return;

        List<Color> inicialColors = new ArrayList<>();
        inicialColors.add(new Color("Blanco","#f1f1f1"));
        inicialColors.add(new Color("Café","#A54E3C"));
        inicialColors.add(new Color("Gris","#f1f1f1"));
        inicialColors.add(new Color("Negro","#f1f1f1"));
        inicialColors.add(new Color("Atigrado","#f1f1f1"));
        inicialColors.add(new Color("Bicolor","#f1f1f1"));
        inicialColors.add(new Color("Con Manchas","#f1f1f1"));
        inicialColors.add(new Color("Varios Colores","#f1f1f1"));
        colorRepository.saveAll(inicialColors);        
    }
   
}
