//package com.nnk.springboot;
//
//import com.nnk.springboot.domain.CurvePoint;
//import com.nnk.springboot.repositories.CurvePointRepository;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CurvePointTests {
//
//	private static AutoCloseable mocks;
//
//	@InjectMocks
//	private CurvePointRepository curvePointRepository;
//
//	@BeforeEach
//	protected void setUp() {
//		mocks = MockitoAnnotations.openMocks(this);
//	}
//
//	@AfterAll
//	public static void tearDown() throws Exception {
//		if (null != mocks){
//			mocks.close();
//		}
//	}
//
//	@Test
//	public void curvePointTest() {
//		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
//
//		// Save
//		curvePoint = curvePointRepository.save(curvePoint);
//		assertNotNull(curvePoint.getId());
//		assertTrue(curvePoint.getCurveId() == 10);
//
//		// Update
//		curvePoint.setCurveId(20);
//		curvePoint = curvePointRepository.save(curvePoint);
//		assertTrue(curvePoint.getCurveId() == 20);
//
//		// Find
//		List<CurvePoint> listResult = curvePointRepository.findAll();
//		assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = curvePoint.getId();
//		curvePointRepository.delete(curvePoint);
//		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
//		assertFalse(curvePointList.isPresent());
//	}
//
//}
