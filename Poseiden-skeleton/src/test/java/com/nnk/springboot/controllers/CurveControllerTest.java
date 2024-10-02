package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.record.CurvePointRecord;
import com.nnk.springboot.service.CurvePointService;
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

public class CurveControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private CurveController curveController;

    @Mock
    private CurvePointService curvePointService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        mocks =MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (null != mocks) {
            mocks.close();
        }
    }

    @Test
    public void homeTest() {
        List<CurvePointRecord.Vm.CurvePointVm> curvePoints = List.of(mock(CurvePointRecord.Vm.CurvePointVm.class));
        when(curvePointService.getAllCurvePoints()).thenReturn(curvePoints);

        String viewName = curveController.home(model);

        verify(model, times(1)).addAttribute("curvePoints", curvePoints);
        assertEquals("curvePoint/list", viewName);
    }

    @Test
    public void addCurveFormTest() {
        String viewName = curveController.addCurveForm(new CurvePoint());

        assertEquals("curvePoint/add", viewName);
    }

    @Test
    public void validateCurveSuccessTest() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = curveController.validate(new CurvePoint(), bindingResult, model);

        verify(curvePointService, times(1)).createCurvePoint(any(CurvePoint.class));
        assertEquals("redirect:/curvePoint/list", viewName);
    }

    @Test
    public void validateCurveWithErrorsTest() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curveController.validate(new CurvePoint(), bindingResult, model);

        assertEquals("curvePoint/add", viewName);
        verify(curvePointService, never()).createCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void showUpdateFormTest() {
        int id = 1;
        CurvePointRecord.Vm.CurvePointVm curvePointVm = mock(CurvePointRecord.Vm.CurvePointVm.class);
        when(curvePointService.getCurvePointById(id)).thenReturn(curvePointVm);

        String viewName = curveController.showUpdateForm(id, model);

        verify(model, times(1)).addAttribute("curvePoint", curvePointVm);
        assertEquals("curvePoint/update", viewName);
    }

    @Test
    public void showUpdateFormNotFoundTest() {
        int id = 1;
        when(curvePointService.getCurvePointById(id)).thenReturn(null);

        String viewName = curveController.showUpdateForm(id, model);

        assertEquals("redirect:/curvePoint/list", viewName);
    }

    @Test
    public void updateCurveSuccessTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        CurvePointRecord.Vm.CurvePointVm curvePointVm = mock(CurvePointRecord.Vm.CurvePointVm.class);
        when(curvePointService.updateCurvePoint(eq(id), any(CurvePoint.class))).thenReturn(curvePointVm);

        String viewName = curveController.updateCurve(id, new CurvePoint(), bindingResult, model);

        verify(curvePointService, times(1)).updateCurvePoint(eq(id), any(CurvePoint.class));
        verify(model, times(1)).addAttribute("curvePoint", curvePointVm);
        assertEquals("redirect:/curvePoint/list", viewName);
    }

    @Test
    public void updateCurveWithErrorsTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curveController.updateCurve(id, new CurvePoint(), bindingResult, model);

        assertEquals("curvePoint/update", viewName);
        verify(curvePointService, never()).updateCurvePoint(anyInt(), any(CurvePoint.class));
    }

    @Test
    public void deleteCurveTest() {
        int id = 1;

        String viewName = curveController.deleteCurve(id, model);

        verify(curvePointService, times(1)).deleteCurvePoint(id);
        verify(model, times(1)).addAttribute(eq("curvePoint"), any());
        assertEquals("redirect:/curvePoint/list", viewName);
    }
}
