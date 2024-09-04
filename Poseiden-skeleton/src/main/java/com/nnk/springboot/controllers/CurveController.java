package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.record.CurvePointRecord;
import com.nnk.springboot.service.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CurveController {

    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePointRecord.Vm.CurvePointVm> curvePointVms = curvePointService.getAllCurvePoints();
        model.addAttribute("curvePoints", curvePointVms);

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors())
            return "curvePoint/add";

        curvePointService.createCurvePoint(curvePoint);
        model.addAttribute("curvePoint", curvePointService.getAllCurvePoints());

        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePointRecord.Vm.CurvePointVm curvePointVm = curvePointService.getCurvePointById(id);
        if (null != curvePointVm) {
            model.addAttribute("curvePoint", curvePointVm);
            return "curvePoint/update";
        } else {
            return "redirect:/curvePoint/list";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors())
            return "curvePoint/update";

        CurvePointRecord.Vm.CurvePointVm curvePointVm = curvePointService.updateCurvePoint(id, curvePoint);
        model.addAttribute("curvePoint", curvePointVm);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
        model.addAttribute("curvePoint", curvePointService.getAllCurvePoints());

        return "redirect:/curvePoint/list";
    }
}
