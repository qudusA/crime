package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WardenRanksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rank", unique = true)
    private String rank;

    @OneToMany(mappedBy = "wardenRanks", orphanRemoval = true)
    private List<PoliceWardenJudgeEntity> policeWardenJudge;
}
