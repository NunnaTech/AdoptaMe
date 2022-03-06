package mx.com.adoptame.entities.user;

import java.io.Serializable;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.profile.Profile;

@Data
@NoArgsConstructor
@ToString
public class User  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String email;
    
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String password;
    
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "link_restore_password", columnDefinition = "varchar(150)")
    private String linkRestorePassword;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    // Relationships
    @OneToOne(mappedBy = "user")
    private Profile profile;

}
