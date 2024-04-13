package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "complain_entity")
public class ComplainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "crime_type")
    private String crimeType;

    @Column(name = "description")
    private String description;

//    @Column(name = "complaint_image")
//    private String complaintImage;

    @Column(name = "is_Addressed")
    @Builder.Default
    private boolean isAddressed = false;

    @CreatedDate
    @Column(name = "created-date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @CreatedBy
    @Column(name = "created-by", updatable = false, nullable = false)
    private Long createdBy;

    @LastModifiedDate
    @Column(name = "modified-date", insertable = false)
    private LocalDateTime modifiedDate;

    @LastModifiedBy
    @Column(name = "modified-by", insertable = false)
    private Long modifiedBy;
}
