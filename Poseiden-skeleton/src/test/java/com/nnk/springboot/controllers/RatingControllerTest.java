package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.record.RatingRecord;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RatingControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void homeTest() {
        List<RatingRecord.Vm.RatingVm> ratingVms = List.of(mock(RatingRecord.Vm.RatingVm.class));
        when(ratingService.getAllRating()).thenReturn(ratingVms);

        String viewName = ratingController.home(model);

        verify(model, times(1)).addAttribute("ratings", ratingVms);
        assertEquals("rating/list", viewName);
    }

    @Test
    public void addRatingFormTest() {
        String viewName = ratingController.addRatingForm(new Rating());

        assertEquals("rating/add", viewName);
    }

    @Test
    public void validateRatingSuccessTest() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ratingController.validate(new Rating(), bindingResult, model);

        verify(ratingService, times(1)).createRating(any(Rating.class));
        verify(model, times(1)).addAttribute(eq("ratings"), any());
        assertEquals("rating/add", viewName);
    }

    @Test
    public void validateRatingWithErrorsTest() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ratingController.validate(new Rating(), bindingResult, model);

        assertEquals("rating/add", viewName);
        verify(ratingService, never()).createRating(any(Rating.class));
    }

    @Test
    public void showUpdateFormTest() {
        int id = 1;
        RatingRecord.Vm.RatingVm ratingVm = mock(RatingRecord.Vm.RatingVm.class);
        when(ratingService.getRatingById(id)).thenReturn(ratingVm);

        String viewName = ratingController.showUpdateForm(id, model);

        verify(model, times(1)).addAttribute("rating", ratingVm);
        assertEquals("rating/update", viewName);
    }

    @Test
    public void showUpdateFormNotFoundTest() {
        int id = 1;
        when(ratingService.getRatingById(id)).thenReturn(null);

        String viewName = ratingController.showUpdateForm(id, model);

        assertEquals("redirect:rating/list", viewName);
    }

    @Test
    public void updateRatingSuccessTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        RatingRecord.Vm.RatingVm ratingVm = mock(RatingRecord.Vm.RatingVm.class);
        when(ratingService.updateRating(eq(id), any(Rating.class))).thenReturn(ratingVm);

        String viewName = ratingController.updateRating(id, new Rating(), bindingResult, model);

        verify(ratingService, times(1)).updateRating(eq(id), any(Rating.class));
        verify(model, times(1)).addAttribute("ratings", ratingVm);
        assertEquals("redirect:/rating/list", viewName);
    }

    @Test
    public void updateRatingWithErrorsTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ratingController.updateRating(id, new Rating(), bindingResult, model);

        assertEquals("rating/update", viewName);
        verify(ratingService, never()).updateRating(anyInt(), any(Rating.class));
    }

    @Test
    public void deleteRatingTest() {
        int id = 1;

        String viewName = ratingController.deleteRating(id, model);

        verify(ratingService, times(1)).deleteRating(id);
        verify(model, times(1)).addAttribute(eq("ratings"), any());
        assertEquals("redirect:/rating/list", viewName);
    }
}
