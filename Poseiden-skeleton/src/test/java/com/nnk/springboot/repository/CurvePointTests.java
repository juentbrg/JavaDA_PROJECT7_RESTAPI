package com.nnk.springboot.repository;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CurvePointTests {

	private static AutoCloseable mocks;

    private CurvePoint curvePoint;

	@Mock
	private CurvePointRepository curvePointRepository;

	@BeforeEach
	protected void setUp() {
        curvePoint = new CurvePoint(10, 10d, 30d);
		mocks = MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		if (null != mocks){
			mocks.close();
		}
	}

	@Test
	public void curvePointSaveTest() {
        when(curvePointRepository.save(curvePoint)).thenAnswer(invocation -> {
            curvePoint.setId(1);
            return curvePoint;
        });

		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePoint.getId());
		assertTrue(curvePoint.getCurveId() == 10);
	}

    @Test
    public void curvePointUpdateTest() {
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        curvePoint.setCurveId(20);
        curvePoint = curvePointRepository.save(curvePoint);
        assertTrue(curvePoint.getCurveId() == 20);
    }

    @Test
    public void curvePointFindAllTest() {
        when(curvePointRepository.findAll()).thenReturn(List.of(curvePoint));

        List<CurvePoint> listResult = curvePointRepository.findAll();
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void curvePointDeleteTest() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
        assertFalse(curvePointList.isPresent());
    }
}
