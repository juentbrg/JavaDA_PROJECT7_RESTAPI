package com.nnk.springboot.record;

import com.nnk.springboot.domain.BidList;

import java.time.LocalDateTime;

public interface BidListRecord {

    interface Vm{

        record BidListVm(
                String account,
                String type,
                Double bidQuantity,
                Double askQuantity,
                Double bid,
                Double ask,
                String benchmark,
                LocalDateTime bidListDate,
                String commentary,
                String security,
                String status,
                String trader,
                String book,
                String creationName,
                LocalDateTime creationDate,
                String revisionName,
                LocalDateTime revisionDate,
                String dealName,
                String dealType,
                String sourceListId,
                String side
        ){
            public BidListVm(BidList bidList) {
                this(
                        bidList.getAccount(),
                        bidList.getType(),
                        bidList.getBidQuantity(),
                        bidList.getAskQuantity(),
                        bidList.getBid(),
                        bidList.getAsk(),
                        bidList.getBenchmark(),
                        bidList.getBidListDate(),
                        bidList.getCommentary(),
                        bidList.getSecurity(),
                        bidList.getStatus(),
                        bidList.getTrader(),
                        bidList.getBook(),
                        bidList.getCreationName(),
                        bidList.getCreationDate(),
                        bidList.getRevisionName(),
                        bidList.getRevisionDate(),
                        bidList.getDealName(),
                        bidList.getDealType(),
                        bidList.getSourceListId(),
                        bidList.getSide()
                );
            }
        }
    }
}
