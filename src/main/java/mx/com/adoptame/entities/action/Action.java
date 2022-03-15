package mx.com.adoptame.entities.action;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.log.Log;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotNull
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 150)
    @Column(columnDefinition = "varchar(150)")
    private String icon;

    @OneToMany(mappedBy="action", cascade = CascadeType.PERSIST)
    private Set<Log> logs;
}
