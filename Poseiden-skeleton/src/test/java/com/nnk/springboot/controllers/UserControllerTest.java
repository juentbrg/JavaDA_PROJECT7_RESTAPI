package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

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
        String viewName = userController.home(model);

        verify(userRepository, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("users"), any());
        assertEquals("user/list", viewName);
    }

    @Test
    public void addUserTest() {
        String viewName = userController.addUser(new User());

        assertEquals("user/add", viewName);
    }

    @Test
    public void validateUserSuccessTest() {
        when(bindingResult.hasErrors()).thenReturn(false);

        User user = new User();
        user.setPassword("password123");

        String viewName = userController.validate(user, bindingResult, model);

        verify(userRepository, times(1)).save(any(User.class));
        verify(model, times(1)).addAttribute(eq("users"), any());
        assertEquals("redirect:/user/list", viewName);
    }


    @Test
    public void validateUserWithErrorsTest() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.validate(new User(), bindingResult, model);

        assertEquals("user/add", viewName);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void showUpdateFormTest() {
        int id = 1;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        String viewName = userController.showUpdateForm(id, model);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("user/update", viewName);
    }

    @Test
    public void showUpdateFormNotFoundTest() {
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.showUpdateForm(id, model);
        });

        assertTrue(exception.getMessage().contains("Invalid user Id:"));
    }

    @Test
    public void updateUserSuccessTest() {
        when(bindingResult.hasErrors()).thenReturn(false);

        User user = new User();
        user.setPassword("password123");

        String viewName = userController.updateUser(1, user, bindingResult, model);

        verify(userRepository, times(1)).save(any(User.class));
        verify(model, times(1)).addAttribute(eq("users"), any());
        assertEquals("redirect:/user/list", viewName);
    }


    @Test
    public void updateUserWithErrorsTest() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.updateUser(id, new User(), bindingResult, model);

        assertEquals("user/update", viewName);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void deleteUserTest() {
        int id = 1;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        String viewName = userController.deleteUser(id, model);

        verify(userRepository, times(1)).delete(user);
        verify(model, times(1)).addAttribute(eq("users"), any());
        assertEquals("redirect:/user/list", viewName);
    }

    @Test
    public void deleteUserNotFoundTest() {
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.deleteUser(id, model);
        });

        assertTrue(exception.getMessage().contains("Invalid user Id:"));
    }
}
