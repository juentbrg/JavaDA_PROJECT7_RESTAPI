package com.nnk.springboot.record;

import com.nnk.springboot.domain.Trade;

import java.time.LocalDateTime;

public interface TradeRecord {

    interface Vm {

        record TradeVm(
                String account,
                String type,
                Double buyQuantity,
                Double sellQuantity,
                Double buyPrice,
                Double sellPrice,
                LocalDateTime tradeDate,
                String security,
                String status,
                String trader,
                String benchmark,
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
            public TradeVm(Trade trade){
                this(
                        trade.getAccount(),
                        trade.getType(),
                        trade.getBuyQuantity(),
                        trade.getSellQuantity(),
                        trade.getBuyPrice(),
                        trade.getSellPrice(),
                        trade.getTradeDate(),
                        trade.getSecurity(),
                        trade.getStatus(),
                        trade.getTrader(),
                        trade.getBenchmark(),
                        trade.getBook(),
                        trade.getCreationName(),
                        trade.getCreationDate(),
                        trade.getRevisionName(),
                        trade.getRevisionDate(),
                        trade.getDealName(),
                        trade.getDealType(),
                        trade.getSourceListId(),
                        trade.getSide()
                );
            }
        }
    }

    interface Api {

        record TradeRequest(
                String account,
                String type,
                Double buyQuantity,
                Double sellQuantity,
                Double buyPrice,
                Double sellPrice,
                LocalDateTime tradeDate,
                String security,
                String status,
                String trader,
                String benchmark,
                String book,
                String creationName,
                String revisionName,
                LocalDateTime revisionDate,
                String dealName,
                String dealType,
                String sourceListId,
                String side
        ){}
    }
}
