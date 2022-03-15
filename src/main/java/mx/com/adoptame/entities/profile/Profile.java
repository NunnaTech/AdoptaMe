package mx.com.adoptame.entities.profile;

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
import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.user.User;

@Entity
@Table(name = "TBL_PROFILES")
@Data
@NoArgsConstructor
@ToString
public class Profile implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private Integer id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(name="last_name", nullable = false, columnDefinition = "varchar(50)")
    private String lastName;
    
    @Column(name="second_name", columnDefinition = "varchar(50)")
    private String secondName;

    @Column(columnDefinition = "varchar(17)")
    private String phone;

    @Column(columnDefinition = "varchar(150)")
    private String image;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false, columnDefinition="TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition="TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
