package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.record.TradeRecord;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class TradeService {

    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Transactional(readOnly = true)
    public List<TradeRecord.Vm.TradeVm> getAllTrade() {
        logger.info("Fetching all trades");
        List<Trade> tradeList = tradeRepository.findAll();
        logger.debug("Found {} trades", tradeList.size());

        if (tradeList.isEmpty()) {
            logger.warn("No trades found");
            return null;
        }

        return tradeList
                .stream()
                .map(TradeRecord.Vm.TradeVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public TradeRecord.Vm.TradeVm getTradeById(int id) {
        logger.info("Fetching trade with id: {}", id);
        Optional<Trade> tradeOpt = tradeRepository.findById(id);

        if (tradeOpt.isPresent()) {
            logger.debug("Trade found with id: {}", id);
            return new TradeRecord.Vm.TradeVm(tradeOpt.get());
        }

        logger.warn("No trade found with id: {}", id);
        return null;
    }

    @Transactional
    public void createTrade(Trade trade) {
        try {
            logger.info("Creating new trade");
            tradeRepository.save(trade);
            logger.debug("Trade created with id: {}", trade.getTradeId());
        } catch (Exception e) {
            logger.error("Failed to create trade", e);
            throw new RuntimeException("Failed to create transaction", e);
        }
    }

    @Transactional
    public TradeRecord.Vm.TradeVm updateTrade(int id, Trade tradeRequest) {
        logger.info("Updating trade with id: {}", id);
        Optional<Trade> tradeOpt = tradeRepository.findById(id);

        if (tradeOpt.isPresent()) {
            Trade trade = tradeOpt.get();
            BeanUtils.copyProperties(tradeRequest, trade, getNullPropertyNames(tradeRequest));
            tradeRepository.save(trade);
            logger.debug("Trade updated with id: {}", id);
            return new TradeRecord.Vm.TradeVm(trade);
        }

        logger.warn("No trade found with id: {} for update", id);
        return null;
    }

    @Transactional
    public void deleteTrade(int id) {
        try {
            logger.info("Deleting trade with id: {}", id);
            tradeRepository.deleteById(id);
            logger.debug("Trade deleted with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete trade with id: {}", id, e);
            throw new RuntimeException("Failed to delete trade", e);
        }
    }
}
