package com.nnk.springboot.controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

public class HomeControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private HomeController homeController;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        mocks =MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (null != mocks) {
            mocks.close();
        }
    }

    @Test
    public void homeTest() {
        String viewName = homeController.home(model);
        assertEquals("home", viewName);
    }

    @Test
    public void adminHomeTest() {
        String viewName = homeController.adminHome(model);
        assertEquals("redirect:/bidList/list", viewName);
    }
}
