package mx.com.adoptame.entities.donation;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.user.User;

@Entity
@Table(name = "TBL_DONATIONS")
@Data
@NoArgsConstructor
@ToString
public class Donation implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donation")
    private Integer id;

    @Column(nullable = false, columnDefinition = "double(10,2)")
    private Double quantity;
    
    @Column(name = "is_completed", nullable = false, columnDefinition = "TINYINT default 0")
    private Boolean isCompleted;

    @Column(columnDefinition = "TEXT")
    private String authorization;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition="TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition="TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}

