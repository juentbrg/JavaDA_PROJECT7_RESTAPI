package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RatingTests {

	private static AutoCloseable mocks;

    private Rating rating;

	@Mock
	private RatingRepository ratingRepository;

	@BeforeEach
	protected void setUp() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		mocks = MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		if (null != mocks){
			mocks.close();
		}
	}

	@Test
	public void ratingSaveTest() {
        when(ratingRepository.save(rating)).thenAnswer(invocation -> {
            rating.setId(1);
            return rating;
        });

		rating = ratingRepository.save(rating);
		assertNotNull(rating.getId());
		assertTrue(rating.getOrderNumber() == 10);
	}

    @Test
    public void ratingUpdateTest() {
        when(ratingRepository.save(rating)).thenReturn(rating);

        rating.setOrderNumber(20);
        rating = ratingRepository.save(rating);
        assertTrue(rating.getOrderNumber() == 20);
    }

    @Test
    public void ratingFindAllTest() {
        when(ratingRepository.findAll()).thenReturn(List.of(rating));

        List<Rating> listResult = ratingRepository.findAll();
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void ratingDeleteTest() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Integer id = rating.getId();
        ratingRepository.delete(rating);
        Optional<Rating> ratingList = ratingRepository.findById(id);
        assertFalse(ratingList.isPresent());
    }
}
