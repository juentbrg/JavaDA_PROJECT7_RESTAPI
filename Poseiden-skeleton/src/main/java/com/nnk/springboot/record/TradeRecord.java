package com.nnk.springboot.record;

import com.nnk.springboot.domain.Trade;

public interface TradeRecord {

    interface Vm {

        record TradeVm(
                Integer tradeId,
                String account,
                String type,
                Double buyQuantity
        ){
            public TradeVm(Trade trade){
                this(
                        trade.getTradeId(),
                        trade.getAccount(),
                        trade.getType(),
                        trade.getBuyQuantity()
                );
            }
        }
    }
}
