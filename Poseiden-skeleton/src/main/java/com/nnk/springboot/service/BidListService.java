package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.record.BidListRecord;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class BidListService {

    private static final Logger logger = LoggerFactory.getLogger(BidListService.class);

    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Transactional(readOnly = true)
    public List<BidListRecord.Vm.BidListVm> getAllBidList() {
        logger.info("Fetching all bid lists");
        List<BidList> allBidList = bidListRepository.findAll();
        logger.debug("Found {} bid lists", allBidList.size());
        return allBidList
                .stream()
                .map(BidListRecord.Vm.BidListVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public BidListRecord.Vm.BidListVm getBidListById(int id) {
        logger.info("Fetching bid list with id: {}", id);
        Optional<BidList> bidListOpt = bidListRepository.findById(id);

        if (bidListOpt.isPresent()) {
            logger.debug("Bid list found with id: {}", id);
            return new BidListRecord.Vm.BidListVm(bidListOpt.get());
        }

        logger.warn("No bid list found with id: {}", id);
        return null;
    }

    @Transactional
    public void createBidList(BidList bid) {
        try {
            if (null != bid) {
                logger.info("Creating new bid list");
                bidListRepository.save(bid);
                logger.debug("Bid list created with id: {}", bid.getBidListId());
            } else {
                logger.warn("Attempted to create null bid list");
            }
        } catch (Exception e) {
            logger.error("Failed to create bid list", e);
            throw new RuntimeException("Failed to create bid list", e);
        }
    }

    @Transactional
    public BidListRecord.Vm.BidListVm updateBidList(int bidListId, BidList bidListRequest) {
        logger.info("Updating bid list with id: {}", bidListId);
        Optional<BidList> bidListOpt = bidListRepository.findById(bidListId);

        if (bidListOpt.isPresent()) {
            BidList bidList = bidListOpt.get();
            BeanUtils.copyProperties(bidListRequest, bidList, getNullPropertyNames(bidListRequest));
            bidListRepository.save(bidList);
            logger.debug("Bid list updated with id: {}", bidListId);
            return new BidListRecord.Vm.BidListVm(bidList);
        }

        logger.warn("No bid list found with id: {} for update", bidListId);
        return null;
    }

    @Transactional
    public void deleteBidListById(int id) {
        try {
            logger.info("Deleting bid list with id: {}", id);
            bidListRepository.deleteById(id);
            logger.debug("Bid list deleted with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete bid list with id: {}", id, e);
            throw new RuntimeException("Delete bid list failed", e);
        }
    }
}
