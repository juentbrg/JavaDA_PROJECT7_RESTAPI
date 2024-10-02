package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.record.BidListRecord;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Transactional(readOnly = true)
    public List<BidListRecord.Vm.BidListVm> getAllBidList() {
        List<BidList> allBidList = bidListRepository.findAll();

        return allBidList
                .stream()
                .map(BidListRecord.Vm.BidListVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public BidListRecord.Vm.BidListVm getBidListById(int id) {
        Optional<BidList> bidListOpt = bidListRepository.findById(id);

        if (bidListOpt.isPresent())
            return new BidListRecord.Vm.BidListVm(bidListOpt.get());

        return null;
    }

    @Transactional
    public void createBidList(BidList bid) {
        try {
            if (null != bid) {
                bidListRepository.save(bid);
            }
        } catch (Exception e) {
            throw new RuntimeException("failed to create bid list",e);
        }
    }

    @Transactional
    public BidListRecord.Vm.BidListVm updateBidList(int bidListId, BidList bidListRequest) {
        Optional<BidList> bidListOpt = bidListRepository.findById(bidListId);

        if (bidListOpt.isPresent()) {
            BidList bidList = bidListOpt.get();

            BeanUtils.copyProperties(bidListRequest, bidList, getNullPropertyNames(bidListRequest));
            bidListRepository.save(bidList);
            return new BidListRecord.Vm.BidListVm(bidList);
        }
        return null;
    }

    @Transactional
    public void deleteBidListById(int id) {
        try {
            bidListRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("delete bid list failed", e);
        }
    }
}
