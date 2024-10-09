package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.record.RuleNameRecord;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class RuleNameController {

    private final RuleNameService ruleNameService;


    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleNameRecord.Vm.RuleNameVm> ruleNameVms = ruleNameService.getAllRuleName();
        model.addAttribute("ruleNames", ruleNameVms);

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors())
            return "ruleName/add";

        ruleNameService.createRuleName(ruleName);
        model.addAttribute("ruleName", ruleNameService.getAllRuleName());

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameRecord.Vm.RuleNameVm ruleNameVm = ruleNameService.getRuleNameById(id);
        if (null != ruleNameVm) {
            model.addAttribute("ruleName", ruleNameVm);
            return "ruleName/update";
        }

        return "ruleName/add";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors())
            return "ruleName/add";

        RuleNameRecord.Vm.RuleNameVm ruleNameVm = ruleNameService.updateRuleName(id, ruleName);
        model.addAttribute("ruleName", ruleNameVm);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        model.addAttribute("ruleName", ruleNameService.getAllRuleName());

        return "redirect:/ruleName/list";
    }
}
