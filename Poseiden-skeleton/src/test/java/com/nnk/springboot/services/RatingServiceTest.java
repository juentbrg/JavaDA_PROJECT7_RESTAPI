package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.record.RatingRecord;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
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

public class RatingServiceTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

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
    public void getAllRatingOkTest() {
        Rating rating = mock(Rating.class);

        when(ratingRepository.findAll()).thenReturn(List.of(rating));

        List<RatingRecord.Vm.RatingVm> result = ratingService.getAllRating();

        assertEquals(List.of(new RatingRecord.Vm.RatingVm(rating)), result);
    }

    @Test
    public void getAllRatingReturnEmptyListTest() {
        when(ratingRepository.findAll()).thenReturn(Collections.emptyList());

        List<RatingRecord.Vm.RatingVm> result = ratingService.getAllRating();

        assertNull(result);
    }

    @Test
    public void getRatingByIdOkTest() {
        Rating rating = mock(Rating.class);
        int ratingId = 1;

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

        RatingRecord.Vm.RatingVm result = ratingService.getRatingById(ratingId);

        assertEquals(new RatingRecord.Vm.RatingVm(rating), result);
    }

    @Test
    public void getRatingByIdReturnNullTest() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        RatingRecord.Vm.RatingVm result = ratingService.getRatingById(1);

        assertNull(result);
    }

    @Test
    public void createRatingOkTest() {
        Rating rating = mock(Rating.class);

        ratingService.createRating(rating);

        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void createRatingThrowErrorTest() {
        Rating rating = mock(Rating.class);

        when(ratingRepository.save(rating)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> ratingService.createRating(rating));
    }

    @Test
    public void updateRatingOkTest() {
        int ratingId = 1;
        Rating rating = mock(Rating.class);
        Rating ratingRequest = mock(Rating.class);

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(rating)).thenReturn(rating);

        RatingRecord.Vm.RatingVm result = ratingService.updateRating(ratingId, ratingRequest);

        assertEquals(new RatingRecord.Vm.RatingVm(rating), result);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void updateRatingNotFoundTest() {
        int ratingId = 1;
        Rating ratingRequest = mock(Rating.class);

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        RatingRecord.Vm.RatingVm result = ratingService.updateRating(ratingId, ratingRequest);

        assertNull(result);
        verify(ratingRepository, never()).save(any(Rating.class));
    }

    @Test
    public void deleteRatingByIdOkTest() {
        int ratingId = 1;

        doNothing().when(ratingRepository).deleteById(ratingId);

        ratingService.deleteRating(ratingId);

        verify(ratingRepository, times(1)).deleteById(ratingId);
    }

    @Test
    public void deleteRatingByIdThrowErrorTest() {
        int ratingId = 1;

        doThrow(new RuntimeException()).when(ratingRepository).deleteById(ratingId);

        assertThrows(RuntimeException.class, () -> ratingService.deleteRating(ratingId));
        verify(ratingRepository, times(1)).deleteById(ratingId);
    }
}
