package mx.com.adoptame.entities.action;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.log.Log;
import mx.com.adoptame.entities.pet.entities.Pet;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TBL_ACTIONS")
@Data
@NoArgsConstructor
@ToString
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private Integer id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String name;

    @Column(columnDefinition = "varchar(150)")
    private String icon;

    @OneToMany(mappedBy="action", cascade = CascadeType.PERSIST)
    private Set<Log> logs;
}
