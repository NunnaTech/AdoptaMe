package mx.com.adoptame.entities.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import mx.com.adoptame.entities.log.Log;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import mx.com.adoptame.entities.donation.Donation;
import mx.com.adoptame.entities.news.News;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.entities.PetAdopted;
import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.role.Role;
import mx.com.adoptame.entities.request.Request;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@Setter
@Getter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 50)
    @Email
    @Column(unique = true, nullable = false, columnDefinition = "varchar(50)")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 100)
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String password;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean enabled;

    @Column(name = "link_restore_password",unique = true, columnDefinition = "varchar(150)")
    private String linkRestorePassword;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Relationships

    @OneToOne(mappedBy = "user")
    private Profile profile;


    @OneToOne(mappedBy = "user")
    private Request request;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<PetAdopted> adoptedPets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Pet> pets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<News> news;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Donation> donations;

    public void addDonation(Donation donation) {
        donations.add(donation);
        donation.setUser(this);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Log> logs;

    // Many to Many: PETS
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "TBL_FAVORITES_PETS",
            joinColumns = @JoinColumn(name = "id_pet"),
            inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<Pet> favoitesPets;

    public void addToFavorite(Pet pet) {
        favoitesPets.add(pet);
        pet.getUsers().add(this);
    }

    public void removeFromFavorite(Pet pet) {
        favoitesPets.remove(pet);
        pet.getUsers().remove(this);
    }

    // Many to Many: ROLES
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Role> roles;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.roles = roles;
    }
}
