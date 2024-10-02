package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.record.BidListRecord;
import com.nnk.springboot.service.BidListService;
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

public class BidListControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidListService bidListService;

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
        if (null != mocks) {
            mocks.close();
        }
    }

    @Test
    public void homeTest() {
        List<BidListRecord.Vm.BidListVm> bidList = List.of(mock(BidListRecord.Vm.BidListVm.class));
        when(bidListService.getAllBidList()).thenReturn(bidList);

        String viewName = bidListController.home(model);

        verify(model, times(1)).addAttribute("bidLists", bidList);
        assertEquals("bidList/list", viewName);
    }

    @Test
    public void addBidFormTest() {
        String viewName = bidListController.addBidForm(new BidList());

        assertEquals("bidList/add", viewName);
    }

    @Test
    public void validateBidSuccessTest() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = bidListController.validate(new BidList(), bindingResult, model);

        verify(bidListService, times(1)).createBidList(any(BidList.class));
        verify(model, times(1)).addAttribute(eq("bidLists"), any());
        assertEquals("redirect:/bidList/list", viewName);
    }

    @Test
    public void validateBidWithErrorsTest() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = bidListController.validate(new BidList(), bindingResult, model);

        assertEquals("bidList/add", viewName);
        verify(bidListService, never()).createBidList(any(BidList.class));
    }

    @Test
    public void showUpdateFormTest() {
        int id = 1;
        BidListRecord.Vm.BidListVm bidListVm = mock(BidListRecord.Vm.BidListVm.class);
        when(bidListService.getBidListById(id)).thenReturn(bidListVm);

        String viewName = bidListController.showUpdateForm(id, model);

        verify(model, times(1)).addAttribute("bid", bidListVm);
        assertEquals("bidList/update", viewName);
    }

    @Test
    public void showUpdateFormNotFoundTest() {
        int id = 1;
        when(bidListService.getBidListById(id)).thenReturn(null);

        String viewName = bidListController.showUpdateForm(id, model);

        assertEquals("redirect:/bidList/list", viewName);
    }

    @Test
    public void updateBidSuccessTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        BidListRecord.Vm.BidListVm bidListVm = mock(BidListRecord.Vm.BidListVm.class);
        when(bidListService.updateBidList(eq(id), any(BidList.class))).thenReturn(bidListVm);

        String viewName = bidListController.updateBid(id, new BidList(), bindingResult, model);

        verify(bidListService, times(1)).updateBidList(eq(id), any(BidList.class));
        verify(model, times(1)).addAttribute("bidLists", bidListVm);
        assertEquals("redirect:/bidList/list", viewName);
    }

    @Test
    public void updateBidWithErrorsTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = bidListController.updateBid(id, new BidList(), bindingResult, model);

        assertEquals("bidList/update", viewName);
        verify(bidListService, never()).updateBidList(anyInt(), any(BidList.class));
    }

    @Test
    public void deleteBidTest() {
        int id = 1;

        String viewName = bidListController.deleteBid(id, model);

        verify(bidListService, times(1)).deleteBidListById(id);
        verify(model, times(1)).addAttribute(eq("bidLists"), any());
        assertEquals("redirect:/bidList/list", viewName);
    }
}
