package com.nnk.springboot.record;

import com.nnk.springboot.domain.BidList;

public interface BidListRecord {

    interface Vm{

        record BidListVm(
            Integer bidListId,
            String account,
            String type,
            Double bidQuantity
        ){
            public BidListVm(BidList bidList) {
                this(
                        bidList.getBidListId(),
                        bidList.getAccount(),
                        bidList.getType(),
                        bidList.getBidQuantity()
                );
            }
        }
    }
}
