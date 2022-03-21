package mx.com.adoptame.entities.news;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import mx.com.adoptame.entities.tag.Tag;
import mx.com.adoptame.entities.user.User;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "TBL_NEWS")
public class News implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_news")
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 10)
    //@Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    //@NotNull
    //@NotBlank
    //@Size(min = 2, max = 150)
    @Column(columnDefinition = "varchar(150)")
    private String image;

    @Column(name = "is_main", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isMain;

    @Column(name = "is_published", nullable = false, columnDefinition = "tinyint default 1")
    private Boolean isPublished;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    // Relationships
    // Many to Many: TAGS
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "TBL_TAGS_NEWS",
      joinColumns = @JoinColumn(name = "id_news"), 
      inverseJoinColumns = @JoinColumn(name = "id_tag"))
     private List<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getNews().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getNews().remove(this);
    }

}
