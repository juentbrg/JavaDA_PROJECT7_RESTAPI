package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.record.TradeRecord;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Transactional(readOnly = true)
    public List<TradeRecord.Vm.TradeVm> getAllTrade() {
        List<Trade> tradeList = tradeRepository.findAll();

        if (tradeList.isEmpty())
            return null;

        return tradeList
                .stream()
                .map(TradeRecord.Vm.TradeVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public TradeRecord.Vm.TradeVm getTradeById(int id) {
        Optional<Trade> tradeOpt = tradeRepository.findById(id);

        if (tradeOpt.isPresent())
            return new TradeRecord.Vm.TradeVm(tradeOpt.get());

        return null;
    }

    @Transactional
    public TradeRecord.Vm.TradeVm createTrade(Trade trade) {
        try {
            tradeRepository.save(trade);
            return new TradeRecord.Vm.TradeVm(trade);
        } catch (Exception e) {
            throw  new RuntimeException("failed to create transaction", e);
        }
    }

    @Transactional
    public TradeRecord.Vm.TradeVm updateTrade(int id, Trade tradeRequest) {
        Optional<Trade> tradeOpt = tradeRepository.findById(id);

        if (tradeOpt.isPresent()) {
            Trade trade = tradeOpt.get();

            BeanUtils.copyProperties(tradeRequest, trade, getNullPropertyNames(tradeRequest));
            tradeRepository.save(trade);
            return new TradeRecord.Vm.TradeVm(trade);
        }

        return null;
    }

    @Transactional
    public void deleteTrade(int id) {
        try {
            tradeRepository.deleteById(id);
        } catch (Exception e) {
            throw  new RuntimeException("failed to delete trade", e);
        }
    }
}
