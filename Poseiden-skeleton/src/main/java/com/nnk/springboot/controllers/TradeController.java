package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.record.TradeRecord;
import com.nnk.springboot.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<TradeRecord.Vm.TradeVm> tradeVms = tradeService.getAllTrade();
        model.addAttribute("trades", tradeVms);

        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) 
            return "trade/add";
        
        tradeService.createTrade(trade);
        model.addAttribute("trade", tradeService.getAllTrade());

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeRecord.Vm.TradeVm tradeVm = tradeService.getTradeById(id);
        if (null != tradeVm) {
            model.addAttribute("trade", tradeVm);
            return "trade/update";
        }

        return "trade/add";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors())
            return "trade/add";

        TradeRecord.Vm.TradeVm tradeVm = tradeService.updateTrade(id, trade);
        model.addAttribute("trade", tradeVm);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        model.addAttribute("trade", tradeService.getAllTrade());

        return "redirect:/trade/list";
    }
}
