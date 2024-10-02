package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.record.TradeRecord;
import com.nnk.springboot.service.TradeService;
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

public class TradeControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeService;

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
        List<TradeRecord.Vm.TradeVm> tradeVms = List.of(mock(TradeRecord.Vm.TradeVm.class));
        when(tradeService.getAllTrade()).thenReturn(tradeVms);

        String viewName = tradeController.home(model);

        verify(model, times(1)).addAttribute("trades", tradeVms);
        assertEquals("trade/list", viewName);
    }

    @Test
    public void addUserTest() {
        String viewName = tradeController.addUser(new Trade());

        assertEquals("trade/add", viewName);
    }

    @Test
    public void validateTradeSuccessTest() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = tradeController.validate(new Trade(), bindingResult, model);

        verify(tradeService, times(1)).createTrade(any(Trade.class));
        verify(model, times(1)).addAttribute(eq("trade"), any());
        assertEquals("trade/add", viewName);
    }

    @Test
    public void validateTradeWithErrorsTest() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.validate(new Trade(), bindingResult, model);

        assertEquals("trade/add", viewName);
        verify(tradeService, never()).createTrade(any(Trade.class));
    }

    @Test
    public void showUpdateFormTest() {
        int id = 1;
        TradeRecord.Vm.TradeVm tradeVm = mock(TradeRecord.Vm.TradeVm.class);
        when(tradeService.getTradeById(id)).thenReturn(tradeVm);

        String viewName = tradeController.showUpdateForm(id, model);

        verify(model, times(1)).addAttribute("trade", tradeVm);
        assertEquals("trade/update", viewName);
    }

    @Test
    public void showUpdateFormNotFoundTest() {
        int id = 1;
        when(tradeService.getTradeById(id)).thenReturn(null);

        String viewName = tradeController.showUpdateForm(id, model);

        assertEquals("trade/add", viewName);
    }

    @Test
    public void updateTradeSuccessTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        TradeRecord.Vm.TradeVm tradeVm = mock(TradeRecord.Vm.TradeVm.class);
        when(tradeService.updateTrade(eq(id), any(Trade.class))).thenReturn(tradeVm);

        String viewName = tradeController.updateTrade(id, new Trade(), bindingResult, model);

        verify(tradeService, times(1)).updateTrade(eq(id), any(Trade.class));
        verify(model, times(1)).addAttribute("trade", tradeVm);
        assertEquals("redirect:/trade/list", viewName);
    }

    @Test
    public void updateTradeWithErrorsTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.updateTrade(id, new Trade(), bindingResult, model);

        assertEquals("trade/add", viewName);
        verify(tradeService, never()).updateTrade(anyInt(), any(Trade.class));
    }

    @Test
    public void deleteTradeTest() {
        int id = 1;

        String viewName = tradeController.deleteTrade(id, model);

        verify(tradeService, times(1)).deleteTrade(id);
        verify(model, times(1)).addAttribute(eq("trade"), any());
        assertEquals("redirect:/trade/list", viewName);
    }
}
