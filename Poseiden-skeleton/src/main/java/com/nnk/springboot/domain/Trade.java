package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Trade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotNull
    @Size(max = 30)
    private String account;

    @NotNull
    @Size(max = 30)
    private String type;

    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;

    private LocalDateTime tradeDate;

    @Size(max = 125)
    private String security;

    @Size(max = 10)
    private String status;

    @Size(max = 125)
    private String trader;

    @Size(max = 125)
    private String benchmark;

    @Size(max = 125)
    private String book;

    @Size(max = 125)
    private String creationName;

    private LocalDateTime creationDate;

    @Size(max = 125)
    private String revisionName;

    private LocalDateTime revisionDate;

    @Size(max = 125)
    private String dealName;

    @Size(max = 125)
    private String dealType;

    @Size(max = 125)
    private String sourceListId;

    @Size(max = 125)
    private String side;
}
