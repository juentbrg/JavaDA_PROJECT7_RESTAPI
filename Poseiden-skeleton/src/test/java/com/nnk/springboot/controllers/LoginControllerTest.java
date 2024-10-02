package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserRepository userRepository;

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
    public void loginTest() {
        ModelAndView mav = loginController.login();
        assertEquals("login", mav.getViewName());
    }

    @Test
    public void getAllUserArticlesTest() {
        when(userRepository.findAll()).thenReturn(List.of());
        ModelAndView mav = loginController.getAllUserArticles();

        assertEquals("user/list", mav.getViewName());
        assertNotNull(mav.getModel().get("users"));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void errorTest() {
        ModelAndView mav = loginController.error();

        assertEquals("403", mav.getViewName());
        assertEquals("You are not authorized for the requested data.", mav.getModel().get("errorMsg"));
    }
}
