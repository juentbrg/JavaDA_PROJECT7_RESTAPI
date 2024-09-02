package com.nnk.springboot.domain;

import com.nnk.springboot.record.BidListRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "BidList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @NotNull
    @Size(max = 30)
    private String account;

    @NotNull
    @Size(max = 30)
    private String type;

    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;

    @Size(max = 125)
    private String benchmark;

    private LocalDateTime bidListDate;

    @Size(max = 125)
    private String commentary;

    @Size(max = 125)
    private String security;

    @Size(max = 10)
    private String status;

    @Size(max = 125)
    private String trader;

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

    public BidList(BidListRecord.Api.BidListRequest bidListRequest) {
        this.account = bidListRequest.account();
        this.type = bidListRequest.type();
        this.bidQuantity = bidListRequest.bidQuantity();
        this.askQuantity = bidListRequest.askQuantity();
        this.bid = bidListRequest.bid();
        this.ask = bidListRequest.ask();
        this.benchmark = bidListRequest.benchmark();
        this.bidListDate = bidListRequest.bidListDate();
        this.commentary = bidListRequest.commentary();
        this.security = bidListRequest.security();
        this.status = bidListRequest.status();
        this.trader = bidListRequest.trader();
        this.book = bidListRequest.book();
        this.creationName = bidListRequest.creationName();
        this.creationDate = LocalDateTime.now();
        this.revisionName = bidListRequest.revisionName();
        this.revisionDate = bidListRequest.revisionDate();
        this.dealName = bidListRequest.dealName();
        this.dealType = bidListRequest.dealType();
        this.sourceListId = bidListRequest.sourceListId();
        this.side = bidListRequest.side();
    }
}
