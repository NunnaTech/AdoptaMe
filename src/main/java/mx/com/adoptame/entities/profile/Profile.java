package mx.com.adoptame.entities.profile;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.user.User;

@Entity
@Table(name = "TBL_PROFILES")
@Setter
@Getter
@NoArgsConstructor

public class Profile implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(name="last_name", nullable = false, columnDefinition = "varchar(50)")
    private String lastName;

    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(name="second_name", columnDefinition = "varchar(50)")
    private String secondName;

    @Size(min = 10, max = 15)
    @Pattern(regexp = "[0-9()-]*")
    @Column(columnDefinition = "varchar(17)")
    private String phone;

    @Size(min = 2, max = 250)
    @Column(columnDefinition = "varchar(250) default 'https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/662/3e95009c-7c93-4580-a764-5a32f1648a0d.jpg'")
    private String image;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id_user")
    private User user;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id_address")
    private Address address;

    public String getFullName(){
        return getName() + " " +getLastName() + " "+getSecondName();
    }

    public String getPartialName(){
        return getName() + " " +getLastName();
    }

    public Profile(String name, String lastName, String secondName, String phone, User user, Address address) {
        this.name = name;
        this.lastName = lastName;
        this.secondName = secondName;
        this.phone = phone;
        this.user = user;
        this.address = address;
        this.image = "https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/662/3e95009c-7c93-4580-a764-5a32f1648a0d.jpg";
    }
}
