package com.nnk.springboot.domain;

import com.nnk.springboot.record.CurvePointRecord;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "CurvePoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer curveId;

    private LocalDateTime asOfDate;

    private Double term;

    private Double value;

    private LocalDateTime creationDate;
}
