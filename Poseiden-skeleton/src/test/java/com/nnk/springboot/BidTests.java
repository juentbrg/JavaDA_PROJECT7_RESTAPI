//package com.nnk.springboot;
//
//import com.nnk.springboot.domain.BidList;
//import com.nnk.springboot.repositories.BidListRepository;
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
//import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
//
//public class BidTests {
//
//	private static AutoCloseable mocks;
//
//	@InjectMocks
//	private BidListRepository bidListRepository;
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
//	public void bidListTest() {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);
//
//		// Save
//		bid = bidListRepository.save(bid);
//		assertNotNull(bid.getBidListId());
//		assertEquals(bid.getBidQuantity(), 10d, 10d);
//
//		// Update
//		bid.setBidQuantity(20d);
//		bid = bidListRepository.save(bid);
//		assertEquals(bid.getBidQuantity(), 20d, 20d);
//
//		// Find
//		List<BidList> listResult = bidListRepository.findAll();
//		assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = bid.getBidListId();
//		bidListRepository.delete(bid);
//		Optional<BidList> bidList = bidListRepository.findById(id);
//		assertFalse(bidList.isPresent());
//	}
//}
