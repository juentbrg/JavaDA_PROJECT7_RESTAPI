//package com.nnk.springboot;
//
//import com.nnk.springboot.domain.Trade;
//import com.nnk.springboot.repositories.TradeRepository;
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
//public class TradeTests {
//
//	private static AutoCloseable mocks;
//
//	@InjectMocks
//	private TradeRepository tradeRepository;
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
//	public void tradeTest() {
//		Trade trade = new Trade("Trade Account", "Type");
//
//		// Save
//		trade = tradeRepository.save(trade);
//		assertNotNull(trade.getTradeId());
//		assertTrue(trade.getAccount().equals("Trade Account"));
//
//		// Update
//		trade.setAccount("Trade Account Update");
//		trade = tradeRepository.save(trade);
//		assertTrue(trade.getAccount().equals("Trade Account Update"));
//
//		// Find
//		List<Trade> listResult = tradeRepository.findAll();
//		assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = trade.getTradeId();
//		tradeRepository.delete(trade);
//		Optional<Trade> tradeList = tradeRepository.findById(id);
//		assertFalse(tradeList.isPresent());
//	}
//}
