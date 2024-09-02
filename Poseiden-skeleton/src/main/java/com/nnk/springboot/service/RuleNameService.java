package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.record.RuleNameRecord;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Transactional(readOnly = true)
    public List<RuleNameRecord.Vm.RuleNameVm> getAllRuleName() {
        List<RuleName> ruleNameList = ruleNameRepository.findAll();

        if (ruleNameList.isEmpty())
            return null;

        return ruleNameList
                .stream()
                .map(RuleNameRecord.Vm.RuleNameVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RuleNameRecord.Vm.RuleNameVm getRuleNameById(int id) {
        Optional<RuleName> ruleNameOpt = ruleNameRepository.findById(id);

        if (ruleNameOpt.isPresent())
            return new RuleNameRecord.Vm.RuleNameVm(ruleNameOpt.get());

        return null;
    }

    @Transactional
    public RuleNameRecord.Vm.RuleNameVm createRuleName(RuleNameRecord.Api.RuleNameRequest ruleNameRequest) {
        try {
            RuleName ruleName = new RuleName(ruleNameRequest);
            ruleNameRepository.save(ruleName);
            return new RuleNameRecord.Vm.RuleNameVm(ruleName);
        } catch (Exception e) {
            throw new RuntimeException("failed to create rule name", e);
        }
    }

    @Transactional
    public RuleNameRecord.Vm.RuleNameVm updateRuleName(int ruleNameId, RuleNameRecord.Api.RuleNameRequest ruleNameRequest) {
        Optional<RuleName> ruleNameOpt = ruleNameRepository.findById(ruleNameId);

        if (ruleNameOpt.isPresent()) {
            RuleName ruleName = ruleNameOpt.get();

            BeanUtils.copyProperties(ruleNameRequest, ruleName, getNullPropertyNames(ruleNameRequest));
            ruleNameRepository.save(ruleName);
            return new RuleNameRecord.Vm.RuleNameVm(ruleName);
        }
        return null;
    }

    @Transactional
    public void deleteRuleName(int ruleNameId) {
        try {
            ruleNameRepository.deleteById(ruleNameId);
        } catch (Exception e) {
            throw new RuntimeException("failed to delete rule name", e);
        }
    }
}
