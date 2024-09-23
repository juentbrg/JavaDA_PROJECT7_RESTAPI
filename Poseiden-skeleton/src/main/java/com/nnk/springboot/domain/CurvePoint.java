package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CurvePoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer curveId;

    private LocalDateTime asOfDate;

    private Double term;

    private Double value;

    private LocalDateTime creationDate;

    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
