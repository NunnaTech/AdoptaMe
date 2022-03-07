package mx.com.adoptame.entities.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.user.User;

@Entity
@Table(name = "TBL_REQUESTS")
@Data
@NoArgsConstructor
@ToString
public class Request implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(nullable = false, columnDefinition = "varchar(140)")
    private String reason;

    @Column(name = "is_accepted", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isAccepted;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false, columnDefinition="TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition="TIMESTAMP")
    private LocalDateTime updatedAt;
    
}
