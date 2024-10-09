package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.record.RuleNameRecord;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class RuleNameService {

    private static final Logger logger = LoggerFactory.getLogger(RuleNameService.class);

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Transactional(readOnly = true)
    public List<RuleNameRecord.Vm.RuleNameVm> getAllRuleName() {
        logger.info("Fetching all rule names");
        List<RuleName> ruleNameList = ruleNameRepository.findAll();
        logger.debug("Found {} rule names", ruleNameList.size());

        if (ruleNameList.isEmpty()) {
            logger.warn("No rule names found");
            return null;
        }

        return ruleNameList
                .stream()
                .map(RuleNameRecord.Vm.RuleNameVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RuleNameRecord.Vm.RuleNameVm getRuleNameById(int id) {
        logger.info("Fetching rule name with id: {}", id);
        Optional<RuleName> ruleNameOpt = ruleNameRepository.findById(id);

        if (ruleNameOpt.isPresent()) {
            logger.debug("Rule name found with id: {}", id);
            return new RuleNameRecord.Vm.RuleNameVm(ruleNameOpt.get());
        }

        logger.warn("No rule name found with id: {}", id);
        return null;
    }

    @Transactional
    public void createRuleName(RuleName ruleName) {
        try {
            logger.info("Creating new rule name");
            ruleNameRepository.save(ruleName);
            logger.debug("Rule name created with id: {}", ruleName.getId());
        } catch (Exception e) {
            logger.error("Failed to create rule name", e);
            throw new RuntimeException("Failed to create rule name", e);
        }
    }

    @Transactional
    public RuleNameRecord.Vm.RuleNameVm updateRuleName(int ruleNameId, RuleName ruleNameRequest) {
        logger.info("Updating rule name with id: {}", ruleNameId);
        Optional<RuleName> ruleNameOpt = ruleNameRepository.findById(ruleNameId);

        if (ruleNameOpt.isPresent()) {
            RuleName ruleName = ruleNameOpt.get();
            BeanUtils.copyProperties(ruleNameRequest, ruleName, getNullPropertyNames(ruleNameRequest));
            ruleNameRepository.save(ruleName);
            logger.debug("Rule name updated with id: {}", ruleNameId);
            return new RuleNameRecord.Vm.RuleNameVm(ruleName);
        }

        logger.warn("No rule name found with id: {} for update", ruleNameId);
        return null;
    }

    @Transactional
    public void deleteRuleName(int ruleNameId) {
        try {
            logger.info("Deleting rule name with id: {}", ruleNameId);
            ruleNameRepository.deleteById(ruleNameId);
            logger.debug("Rule name deleted with id: {}", ruleNameId);
        } catch (Exception e) {
            logger.error("Failed to delete rule name with id: {}", ruleNameId, e);
            throw new RuntimeException("Failed to delete rule name", e);
        }
    }
}
