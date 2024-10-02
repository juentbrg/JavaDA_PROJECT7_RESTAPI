package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.record.RuleNameRecord;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class RuleNameServiceTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (null != mocks) {
            mocks.close();
        }
    }

    @Test
    public void getAllRuleNameOkTest() {
        RuleName ruleName = mock(RuleName.class);

        when(ruleNameRepository.findAll()).thenReturn(List.of(ruleName));

        List<RuleNameRecord.Vm.RuleNameVm> result = ruleNameService.getAllRuleName();

        assertEquals(List.of(new RuleNameRecord.Vm.RuleNameVm(ruleName)), result);
    }

    @Test
    public void getAllRuleNameReturnEmptyListTest() {
        when(ruleNameRepository.findAll()).thenReturn(Collections.emptyList());

        List<RuleNameRecord.Vm.RuleNameVm> result = ruleNameService.getAllRuleName();

        assertNull(result);
    }

    @Test
    public void getRuleNameByIdOkTest() {
        RuleName ruleName = mock(RuleName.class);
        int ruleNameId = 1;

        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(ruleName));

        RuleNameRecord.Vm.RuleNameVm result = ruleNameService.getRuleNameById(ruleNameId);

        assertEquals(new RuleNameRecord.Vm.RuleNameVm(ruleName), result);
    }

    @Test
    public void getRuleNameByIdReturnNullTest() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuleNameRecord.Vm.RuleNameVm result = ruleNameService.getRuleNameById(1);

        assertNull(result);
    }

    @Test
    public void createRuleNameOkTest() {
        RuleName ruleName = mock(RuleName.class);

        ruleNameService.createRuleName(ruleName);

        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    public void createRuleNameThrowErrorTest() {
        RuleName ruleName = mock(RuleName.class);

        when(ruleNameRepository.save(ruleName)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> ruleNameService.createRuleName(ruleName));
    }

    @Test
    public void updateRuleNameOkTest() {
        int ruleNameId = 1;
        RuleName ruleName = mock(RuleName.class);
        RuleName ruleNameRequest = mock(RuleName.class);

        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        RuleNameRecord.Vm.RuleNameVm result = ruleNameService.updateRuleName(ruleNameId, ruleNameRequest);

        assertEquals(new RuleNameRecord.Vm.RuleNameVm(ruleName), result);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    public void updateRuleNameNotFoundTest() {
        int ruleNameId = 1;
        RuleName ruleNameRequest = mock(RuleName.class);

        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.empty());

        RuleNameRecord.Vm.RuleNameVm result = ruleNameService.updateRuleName(ruleNameId, ruleNameRequest);

        assertNull(result);
        verify(ruleNameRepository, never()).save(any(RuleName.class));
    }

    @Test
    public void deleteRuleNameByIdOkTest() {
        int ruleNameId = 1;

        doNothing().when(ruleNameRepository).deleteById(ruleNameId);

        ruleNameService.deleteRuleName(ruleNameId);

        verify(ruleNameRepository, times(1)).deleteById(ruleNameId);
    }

    @Test
    public void deleteRuleNameByIdThrowErrorTest() {
        int ruleNameId = 1;

        doThrow(new RuntimeException()).when(ruleNameRepository).deleteById(ruleNameId);

        assertThrows(RuntimeException.class, () -> ruleNameService.deleteRuleName(ruleNameId));
        verify(ruleNameRepository, times(1)).deleteById(ruleNameId);
    }
}
