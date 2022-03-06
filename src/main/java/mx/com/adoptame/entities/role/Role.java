package mx.com.adoptame.entities.role;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.user.User;


@Data
@NoArgsConstructor
@ToString
public class Role implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;

    @Column(nullable = false, columnDefinition = "varchar(10)")
    private String type;

    @Column(columnDefinition = "varchar(50)")
    private String description;

    // Relationships

    @OneToMany(mappedBy = "role")
    private List<User> users;

    
}
