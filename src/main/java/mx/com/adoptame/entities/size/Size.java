package mx.com.adoptame.entities.size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.pet.entities.Pet;

@Entity
@Table(name = "TBL_SIZES")
@Data
@NoArgsConstructor
@ToString
public class Size implements Serializable{
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_size")
    private Integer id;

    @NotNull
    @NotBlank
    @javax.validation.constraints.Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String name;

    @javax.validation.constraints.Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.0-9]*")
    @Column(name="size_range", columnDefinition = "varchar(30)")
    private String range;

    @Column(columnDefinition = "tinyint default 1")
    private Boolean status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="size", cascade = CascadeType.PERSIST)
    private Set<Pet> pets;

    public Size(String name) {
        this.name = name;
    }
}
