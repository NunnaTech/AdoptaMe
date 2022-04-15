package mx.com.adoptame.entities.tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import mx.com.adoptame.entities.news.News;

@Entity
@Table(name = "TBL_TAGS")
@Getter
@Setter
@NoArgsConstructor
public class Tag implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(unique = true, nullable = false,columnDefinition = "varchar(20)")
    private String name;

    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(columnDefinition = "varchar(50)")
    private String description;

    @ManyToMany(mappedBy = "tags")
    private List<News> news = new ArrayList<>();

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
