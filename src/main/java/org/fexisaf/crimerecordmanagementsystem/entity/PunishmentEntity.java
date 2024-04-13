package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames =
        {"start_date","end_date","punishment_type", "criminal_id" }))
public class PunishmentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "punishment_id")
    private Long punishmentId;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "charged-case_id")
    private ChargedCaseEntity chargedCaseId;

//    (e.g., imprisonment, fine, community service)
    @Column(name = "punishment_type")
    private String punishmentType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "punishment_description")
    private String punishmentDescription;

    @Column(name = "fine_amount")
    private Integer fineAmount;

    @Column(name = "punishment_status")
    private String punishmentStatus;

    @Column(name = "amount_paid")
    private Integer amountPaid;

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
