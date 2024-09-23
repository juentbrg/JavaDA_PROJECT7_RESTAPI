package com.nnk.springboot.record;

import com.nnk.springboot.domain.BidList;

public interface BidListRecord {

    interface Vm{

        record BidListVm(
            String account,
            String type,
            Double bidQuantity
        ){
            public BidListVm(BidList bidList) {
                this(
                        bidList.getAccount(),
                        bidList.getType(),
                        bidList.getBidQuantity()
                );
            }
        }
    }
}
