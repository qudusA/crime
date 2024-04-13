package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"crime_date", "crime_location","crime_type"}))
public class CrimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crime_id")
    private Long crimeId;

    @OneToMany(mappedBy = "crimeEntities")
    private List<CriminalEntity> criminalEntities;

    @Column(name = "crime_description")
    private String crimeDescription;

    @Column(name = "crime_date")
    private String crimeDate;

    @Column(name = "crime_location")
    private String crimeLocation;

//    (e.g., theft, assault, murder)
    @Column(name = "crime_type")
    private String crimeType;

    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "investigating_officer")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserEntity investigatingOfficer;

//    (e.g., open, closed, under investigation)
   @Column(name = "status")
    private String status;

    @Column(name = "image", length = 5000)
    private String image;

    @Column(name = "video", length = 5000)
    private String video;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;


}
