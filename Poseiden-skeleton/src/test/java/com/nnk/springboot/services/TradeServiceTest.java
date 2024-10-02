package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.record.TradeRecord;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
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

public class TradeServiceTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

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
    public void getAllTradeOkTest() {
        Trade trade = mock(Trade.class);

        when(tradeRepository.findAll()).thenReturn(List.of(trade));

        List<TradeRecord.Vm.TradeVm> result = tradeService.getAllTrade();

        assertEquals(List.of(new TradeRecord.Vm.TradeVm(trade)), result);
    }

    @Test
    public void getAllTradeReturnEmptyListTest() {
        when(tradeRepository.findAll()).thenReturn(Collections.emptyList());

        List<TradeRecord.Vm.TradeVm> result = tradeService.getAllTrade();

        assertNull(result);
    }

    @Test
    public void getTradeByIdOkTest() {
        Trade trade = mock(Trade.class);
        int tradeId = 1;

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(trade));

        TradeRecord.Vm.TradeVm result = tradeService.getTradeById(tradeId);

        assertEquals(new TradeRecord.Vm.TradeVm(trade), result);
    }

    @Test
    public void getTradeByIdReturnNullTest() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        TradeRecord.Vm.TradeVm result = tradeService.getTradeById(1);

        assertNull(result);
    }

    @Test
    public void createTradeOkTest() {
        Trade trade = mock(Trade.class);

        tradeService.createTrade(trade);

        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void createTradeThrowErrorTest() {
        Trade trade = mock(Trade.class);

        when(tradeRepository.save(trade)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> tradeService.createTrade(trade));
    }

    @Test
    public void updateTradeOkTest() {
        int tradeId = 1;
        Trade trade = mock(Trade.class);
        Trade tradeRequest = mock(Trade.class);

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(trade)).thenReturn(trade);

        TradeRecord.Vm.TradeVm result = tradeService.updateTrade(tradeId, tradeRequest);

        assertEquals(new TradeRecord.Vm.TradeVm(trade), result);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void updateTradeNotFoundTest() {
        int tradeId = 1;
        Trade tradeRequest = mock(Trade.class);

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.empty());

        TradeRecord.Vm.TradeVm result = tradeService.updateTrade(tradeId, tradeRequest);

        assertNull(result);
        verify(tradeRepository, never()).save(any(Trade.class));
    }

    @Test
    public void deleteTradeByIdOkTest() {
        int tradeId = 1;

        doNothing().when(tradeRepository).deleteById(tradeId);

        tradeService.deleteTrade(tradeId);

        verify(tradeRepository, times(1)).deleteById(tradeId);
    }

    @Test
    public void deleteTradeByIdThrowErrorTest() {
        int tradeId = 1;

        doThrow(new RuntimeException()).when(tradeRepository).deleteById(tradeId);

        assertThrows(RuntimeException.class, () -> tradeService.deleteTrade(tradeId));
        verify(tradeRepository, times(1)).deleteById(tradeId);
    }
}
