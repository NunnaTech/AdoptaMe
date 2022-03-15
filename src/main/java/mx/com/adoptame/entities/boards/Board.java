package mx.com.adoptame.entities.boards;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.log.Log;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TBL_BOARDS")
@Data
@NoArgsConstructor
@ToString
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_board")
    private Integer id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String name;

    @Column(columnDefinition = "varchar(150)")
    private String icon;

    @OneToMany(mappedBy="board", cascade = CascadeType.PERSIST)
    private Set<Log> logs;
}
