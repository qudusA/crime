package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.fexisaf.crimerecordmanagementsystem.constraint.FineAmountRequired;

@Setter
@Getter
@FineAmountRequired
public class PunishmentModel {


    //    (e.g., imprisonment, fine, community service)
    @Column(name = "punishment_type")
    @NotBlank(message = "punishmentType is required")
    @NotBlank(message = "punishmentType cannot be blank")
    private String punishmentType;


    @NotBlank(message = "start is required")
    @NotBlank(message = "start cannot be blank")
    @Column(name = "start_date")
    private String startDate;

    @NotBlank(message = "end date is required")
    @NotBlank(message = "end date cannot be blank")
    @Column(name = "end_date")
    private String endDate;

    @Column(name = "punishment_description")
    @NotBlank(message = "punishment description is required")
    @NotBlank(message = "punishment description cannot be blank")
    private String punishmentDescription;

    @Positive
    @Column(name = "fine_amount")
    private Integer fineAmount;

    @Column(name = "punishment_status")
    private String punishmentStatus;

    @PositiveOrZero
    @Column(name = "amount_paid")
    private Integer amountPaid;



}
