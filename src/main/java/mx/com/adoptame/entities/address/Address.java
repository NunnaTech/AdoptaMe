package mx.com.adoptame.entities.address;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.com.adoptame.entities.profile.Profile;

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
    
    @Column(columnDefinition = "varchar(128)")
    private String references;

    // Relationships
    @OneToOne(mappedBy = "address")
    private Profile profile;
}
