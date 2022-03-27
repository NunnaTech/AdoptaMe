package mx.com.adoptame.entities.pet.entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_PETS_IMAGES")
@Data
@NoArgsConstructor
@ToString
public class PetImage implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sizes")
    private Integer id;
    
    @Column(nullable = false, columnDefinition = "varchar(250)")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pet_id")
    private Pet pet;
}
