package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class BidTests {

	private static AutoCloseable mocks;

	private BidList bid;

	@Mock
	private BidListRepository bidListRepository;

	@BeforeEach
	protected void setUp() {
		bid = new BidList("Account Test", "Type Test", 10d);
		mocks = MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		if (null != mocks){
			mocks.close();
		}
	}

	@Test
	public void bidListSaveTest() {
		when(bidListRepository.save(bid)).thenAnswer(invocation -> {
			bid.setBidListId(1);
			return bid;
		});

		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(bid.getBidQuantity(), 10d, 10d);
	}

	@Test
	public void bidListUpdateTest() {
		when(bidListRepository.save(bid)).thenReturn(bid);

		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);
	}

	@Test
	public void bidListFindAllTest() {
		when(bidListRepository.findAll()).thenReturn(List.of(bid));

		List<BidList> listResult = bidListRepository.findAll();
		assertFalse(listResult.isEmpty());
	}

	@Test
	public void bidListDeleteTest() {
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
