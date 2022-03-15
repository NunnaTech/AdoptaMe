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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.news.News;

@Entity
@Table(name = "TBL_TAGS")
@Data
@NoArgsConstructor
@ToString
public class Tag implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Integer id;

    @Column(unique = true, nullable = false,columnDefinition = "varchar(20)")
    private String name;
    
    @Column(columnDefinition = "varchar(50)")
    private String description;

    @ManyToMany(mappedBy = "tags")
    public List<News> news = new ArrayList<>();
}
