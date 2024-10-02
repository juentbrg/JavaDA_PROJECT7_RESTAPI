package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.record.BidListRecord;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
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

public class BidListServiceTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

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
    public void getAllBidListOkTest() {
        BidList bidList = mock(BidList.class);

        when(bidListRepository.findAll()).thenReturn(List.of(bidList));

        List<BidListRecord.Vm.BidListVm> result = bidListService.getAllBidList();

        assertEquals(List.of(new BidListRecord.Vm.BidListVm(bidList)), result);
    }

    @Test
    public void getAllBidListReturnEmptyListTest() {
        when(bidListRepository.findAll()).thenReturn(Collections.emptyList());

        List<BidListRecord.Vm.BidListVm> result = bidListService.getAllBidList();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getBidListById() {
        BidList bidList = mock(BidList.class);
        int bidListId = 1;

        when(bidListRepository.findById(bidListId)).thenReturn(Optional.ofNullable(bidList));

        BidListRecord.Vm.BidListVm result = bidListService.getBidListById(bidListId);

        assertEquals(new BidListRecord.Vm.BidListVm(bidList), result);
    }

    @Test
    public void getBidListByIdReturnNullTest() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        BidListRecord.Vm.BidListVm result = bidListService.getBidListById(1);

        assertNull(result);
    }

    @Test
    public void createBidListOkTest() {
        BidList bidList = mock(BidList.class);

        bidListService.createBidList(bidList);

        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void createBidListThrowErrorTest() {
        BidList bidList = mock(BidList.class);

        when(bidListRepository.save(bidList)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bidListService.createBidList(bidList));
    }

    @Test
    public void updateBidListOkTest() {
        int bidListId = 1;
        BidList bidList = mock(BidList.class);
        BidList bidListRequest = mock(BidList.class);

        when(bidListRepository.findById(bidListId)).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(bidList)).thenReturn(bidList);

        BidListRecord.Vm.BidListVm result = bidListService.updateBidList(bidListId, bidListRequest);

        assertEquals(new BidListRecord.Vm.BidListVm(bidList), result);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void updateBidListNotFoundTest() {
        int bidListId = 1;
        BidList bidListRequest = mock(BidList.class);

        when(bidListRepository.findById(bidListId)).thenReturn(Optional.empty());

        BidListRecord.Vm.BidListVm result = bidListService.updateBidList(bidListId, bidListRequest);

        assertNull(result);
        verify(bidListRepository, never()).save(any(BidList.class));
    }

    @Test
    public void deleteBidListByIdOkTest() {
        int bidListId = 1;

        doNothing().when(bidListRepository).deleteById(bidListId);

        bidListService.deleteBidListById(bidListId);

        verify(bidListRepository, times(1)).deleteById(bidListId);
    }

    @Test
    public void deleteBidListByIdThrowErrorTest() {
        int bidListId = 1;

        doThrow(new RuntimeException()).when(bidListRepository).deleteById(bidListId);

        assertThrows(RuntimeException.class, () -> bidListService.deleteBidListById(bidListId));
        verify(bidListRepository, times(1)).deleteById(bidListId);
    }
}
