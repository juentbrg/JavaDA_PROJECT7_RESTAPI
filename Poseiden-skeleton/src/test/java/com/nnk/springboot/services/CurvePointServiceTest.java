package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.record.CurvePointRecord;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
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

public class CurvePointServiceTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

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
    public void getAllCurvePointsOkTest() {
        CurvePoint curvePoint = mock(CurvePoint.class);

        when(curvePointRepository.findAll()).thenReturn(List.of(curvePoint));

        List<CurvePointRecord.Vm.CurvePointVm> result = curvePointService.getAllCurvePoints();

        assertEquals(List.of(new CurvePointRecord.Vm.CurvePointVm(curvePoint)), result);
    }

    @Test
    public void getAllCurvePointsReturnEmptyListTest() {
        when(curvePointRepository.findAll()).thenReturn(Collections.emptyList());

        List<CurvePointRecord.Vm.CurvePointVm> result = curvePointService.getAllCurvePoints();

        assertNull(result);
    }

    @Test
    public void getCurvePointByIdOkTest() {
        CurvePoint curvePoint = mock(CurvePoint.class);
        int curvePointId = 1;

        when(curvePointRepository.findById(curvePointId)).thenReturn(Optional.of(curvePoint));

        CurvePointRecord.Vm.CurvePointVm result = curvePointService.getCurvePointById(curvePointId);

        assertEquals(new CurvePointRecord.Vm.CurvePointVm(curvePoint), result);
    }

    @Test
    public void getCurvePointByIdReturnNullTest() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        CurvePointRecord.Vm.CurvePointVm result = curvePointService.getCurvePointById(1);

        assertNull(result);
    }

    @Test
    public void createCurvePointOkTest() {
        CurvePoint curvePoint = mock(CurvePoint.class);

        curvePointService.createCurvePoint(curvePoint);

        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void createCurvePointThrowErrorTest() {
        CurvePoint curvePoint = mock(CurvePoint.class);

        when(curvePointRepository.save(curvePoint)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> curvePointService.createCurvePoint(curvePoint));
    }

    @Test
    public void updateCurvePointOkTest() {
        int curvePointId = 1;
        CurvePoint curvePoint = mock(CurvePoint.class);
        CurvePoint curvePointRequest = mock(CurvePoint.class);

        when(curvePointRepository.findById(curvePointId)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        CurvePointRecord.Vm.CurvePointVm result = curvePointService.updateCurvePoint(curvePointId, curvePointRequest);

        assertEquals(new CurvePointRecord.Vm.CurvePointVm(curvePoint), result);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void updateCurvePointNotFoundTest() {
        int curvePointId = 1;
        CurvePoint curvePointRequest = mock(CurvePoint.class);

        when(curvePointRepository.findById(curvePointId)).thenReturn(Optional.empty());

        CurvePointRecord.Vm.CurvePointVm result = curvePointService.updateCurvePoint(curvePointId, curvePointRequest);

        assertNull(result);
        verify(curvePointRepository, never()).save(any(CurvePoint.class));
    }

    @Test
    public void deleteCurvePointByIdOkTest() {
        int curvePointId = 1;

        doNothing().when(curvePointRepository).deleteById(curvePointId);

        curvePointService.deleteCurvePoint(curvePointId);

        verify(curvePointRepository, times(1)).deleteById(curvePointId);
    }

    @Test
    public void deleteCurvePointByIdThrowErrorTest() {
        int curvePointId = 1;

        doThrow(new RuntimeException()).when(curvePointRepository).deleteById(curvePointId);

        assertThrows(RuntimeException.class, () -> curvePointService.deleteCurvePoint(curvePointId));
        verify(curvePointRepository, times(1)).deleteById(curvePointId);
    }
}
