package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TradeTests {

	private static AutoCloseable mocks;

    private Trade trade;

	@Mock
	private TradeRepository tradeRepository;

	@BeforeEach
	protected void setUp() {
        trade = new Trade("Trade Account", "Type");
		mocks = MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		if (null != mocks){
			mocks.close();
		}
	}

	@Test
	public void tradeSaveTest() {
        when(this.tradeRepository.save(trade)).thenAnswer(invocation -> {
            trade.setTradeId(1);
            return trade;
        });

		trade = tradeRepository.save(trade);
		assertNotNull(trade.getTradeId());
        assertEquals("Trade Account", trade.getAccount());
	}

    @Test
    public void tradeUpdateTest() {
        when(tradeRepository.save(trade)).thenReturn(trade);

        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        assertTrue(trade.getAccount().equals("Trade Account Update"));
    }

    @Test
    public void tradeFindAllTest() {
        when(tradeRepository.findAll()).thenReturn(List.of(trade));

        List<Trade> listResult = tradeRepository.findAll();
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void tradeDeleteTest() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Integer id = trade.getTradeId();
        tradeRepository.delete(trade);
        Optional<Trade> tradeList = tradeRepository.findById(id);
        assertFalse(tradeList.isPresent());
    }
}
