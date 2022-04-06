package mx.com.adoptame.entities.pet.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.character.Character;
import mx.com.adoptame.entities.color.Color;
import mx.com.adoptame.entities.size.Size;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.type.Type;

@Entity
@Table(name = "TBL_PETS")
@Data
@NoArgsConstructor
@ToString
public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Integer id;

    @NotNull
    @NotBlank
    @javax.validation.constraints.Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String name;

    @Column(columnDefinition = "enum ('Cachorro/Cria', 'Joven', 'Adulto') default 'Cachorro/Cria'")
    private String age;

    @Column(nullable = false, columnDefinition = "tinyint")
    private Boolean gender;

    @Column(columnDefinition = "varchar(60)")
    private String breed;

    @Column(name = "is_adopted", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isAdopted;

    @Column(name = "is_active", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorder_by")
    private User recorder;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    // Relationships
    @ManyToMany(mappedBy = "favoitesPets")
    public List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.PERSIST)
    private Set<PetAdopted> pets;

    // Pet to Images
    @OneToMany(mappedBy = "pet", cascade = CascadeType.PERSIST)
    private List<PetImage> images;

    public void addImages(PetImage petImage) {
        images.add(petImage);
        petImage.setPet(this);
    }

    public void deleteImages(PetImage petImage) {
        images.remove(petImage);
        petImage.setPet(null);
    }

}
