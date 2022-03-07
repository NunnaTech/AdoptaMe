package mx.com.adoptame.entities.address;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.profile.Profile;

@Entity
@Table(name = "TBL_ADRESS")
@Data
@NoArgsConstructor
@ToString
public class Address implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String street;

    @Column(name="external_number", nullable = false, columnDefinition = "varchar(5)")
    private String externalNumber;

    @Column(name="internal_number", columnDefinition = "varchar(5)")
    private String internalNumber;

    @Column(name="zip_code", nullable = false, columnDefinition = "varchar(50)")
    private String zipCode;
    
    @Column(name="references_street", columnDefinition = "varchar(128)")
    private String references;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition="TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition="TIMESTAMP")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToOne(mappedBy = "address")
    private Profile profile;
}
