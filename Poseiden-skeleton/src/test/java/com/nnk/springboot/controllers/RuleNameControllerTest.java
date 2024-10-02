package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.record.RuleNameRecord;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RuleNameControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void homeTest() {
        List<RuleNameRecord.Vm.RuleNameVm> ruleNameVms = List.of(mock(RuleNameRecord.Vm.RuleNameVm.class));
        when(ruleNameService.getAllRuleName()).thenReturn(ruleNameVms);

        String viewName = ruleNameController.home(model);

        verify(model, times(1)).addAttribute("ruleNames", ruleNameVms);
        assertEquals("ruleName/list", viewName);
    }

    @Test
    public void addRuleFormTest() {
        String viewName = ruleNameController.addRuleForm(new RuleName());

        assertEquals("ruleName/add", viewName);
    }

    @Test
    public void validateRuleNameSuccessTest() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ruleNameController.validate(new RuleName(), bindingResult, model);

        verify(ruleNameService, times(1)).createRuleName(any(RuleName.class));
        verify(model, times(1)).addAttribute(eq("ruleName"), any());
        assertEquals("ruleName/add", viewName);
    }

    @Test
    public void validateRuleNameWithErrorsTest() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ruleNameController.validate(new RuleName(), bindingResult, model);

        assertEquals("ruleName/add", viewName);
        verify(ruleNameService, never()).createRuleName(any(RuleName.class));
    }

    @Test
    public void showUpdateFormTest() {
        int id = 1;
        RuleNameRecord.Vm.RuleNameVm ruleNameVm = mock(RuleNameRecord.Vm.RuleNameVm.class);
        when(ruleNameService.getRuleNameById(id)).thenReturn(ruleNameVm);

        String viewName = ruleNameController.showUpdateForm(id, model);

        verify(model, times(1)).addAttribute("ruleName", ruleNameVm);
        assertEquals("ruleName/update", viewName);
    }

    @Test
    public void showUpdateFormNotFoundTest() {
        int id = 1;
        when(ruleNameService.getRuleNameById(id)).thenReturn(null);

        String viewName = ruleNameController.showUpdateForm(id, model);

        assertEquals("ruleName/add", viewName);
    }

    @Test
    public void updateRuleNameSuccessTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        RuleNameRecord.Vm.RuleNameVm ruleNameVm = mock(RuleNameRecord.Vm.RuleNameVm.class);
        when(ruleNameService.updateRuleName(eq(id), any(RuleName.class))).thenReturn(ruleNameVm);

        String viewName = ruleNameController.updateRuleName(id, new RuleName(), bindingResult, model);

        verify(ruleNameService, times(1)).updateRuleName(eq(id), any(RuleName.class));
        verify(model, times(1)).addAttribute("ruleName", ruleNameVm);
        assertEquals("redirect:/ruleName/list", viewName);
    }

    @Test
    public void updateRuleNameWithErrorsTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ruleNameController.updateRuleName(id, new RuleName(), bindingResult, model);

        assertEquals("ruleName/add", viewName);
        verify(ruleNameService, never()).updateRuleName(anyInt(), any(RuleName.class));
    }

    @Test
    public void deleteRuleNameTest() {
        int id = 1;

        String viewName = ruleNameController.deleteRuleName(id, model);

        verify(ruleNameService, times(1)).deleteRuleName(id);
        verify(model, times(1)).addAttribute(eq("ruleName"), any());
        assertEquals("redirect:/ruleName/list", viewName);
    }
}
