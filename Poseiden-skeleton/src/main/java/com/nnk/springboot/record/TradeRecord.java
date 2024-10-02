package com.nnk.springboot.record;

import com.nnk.springboot.domain.Trade;

public interface TradeRecord {

    interface Vm {

        record TradeVm(
                Integer id,
                String account,
                String type
        ){
            public TradeVm(Trade trade){
                this(
                        trade.getTradeId(),
                        trade.getAccount(),
                        trade.getType()
                );
            }
        }
    }
}
