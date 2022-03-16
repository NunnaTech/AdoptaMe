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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.profile.Profile;

@Entity
@Table(name = "TBL_ADDRESS")
@Data
@NoArgsConstructor
@ToString
public class Address implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-./#]*")
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String street;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 5)
    @Pattern(regexp = "[0-9]*")
    @Column(name="external_number", nullable = false, columnDefinition = "varchar(5)")
    private String externalNumber;

    @Size(min = 1, max = 5)
    @Pattern(regexp = "[0-9]*")
    @Column(name="internal_number", columnDefinition = "varchar(5)")
    private String internalNumber;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 5)
    @Pattern(regexp = "[0-9]*")
    @Column(name="zip_code", nullable = false, columnDefinition = "varchar(50)")
    private String zipCode;

    @Size(min = 1, max = 128)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-./#]*")
    @Column(name="references_street", columnDefinition = "varchar(128)")
    private String references;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false, columnDefinition="TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition="TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "address")
    private Profile profile;
}
