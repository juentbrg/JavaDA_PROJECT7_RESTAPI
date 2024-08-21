//package com.nnk.springboot;
//
//import com.nnk.springboot.domain.Rating;
//import com.nnk.springboot.repositories.RatingRepository;
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
//public class RatingTests {
//
//	private static AutoCloseable mocks;
//
//	@InjectMocks
//	private RatingRepository ratingRepository;
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
//	public void ratingTest() {
//		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
//
//		// Save
//		rating = ratingRepository.save(rating);
//		assertNotNull(rating.getId());
//		assertTrue(rating.getOrderNumber() == 10);
//
//		// Update
//		rating.setOrderNumber(20);
//		rating = ratingRepository.save(rating);
//		assertTrue(rating.getOrderNumber() == 20);
//
//		// Find
//		List<Rating> listResult = ratingRepository.findAll();
//		assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = rating.getId();
//		ratingRepository.delete(rating);
//		Optional<Rating> ratingList = ratingRepository.findById(id);
//		assertFalse(ratingList.isPresent());
//	}
//}
