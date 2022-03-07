package mx.com.adoptame.entities.color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.pet.entities.Pet;

@Entity
@Table(name = "TBL_COLORS")
@Data
@NoArgsConstructor
@ToString
public class Color implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color")
    private Integer id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(10)")
    private String hex_code;

    @ManyToMany(mappedBy = "colors")
    public List<Pet> pets = new ArrayList<>();

}
