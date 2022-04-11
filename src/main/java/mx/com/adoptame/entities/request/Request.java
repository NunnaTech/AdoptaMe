package mx.com.adoptame.entities.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import mx.com.adoptame.entities.user.User;

@Entity
@Table(name = "TBL_REQUESTS")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Request implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Integer id;

    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")
    @Column(nullable = false, columnDefinition = "varchar(140)")
    private String reason;

    @Column(name = "is_accepted", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isAccepted;

    @Column(name = "is_canceled", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isCanceled;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
