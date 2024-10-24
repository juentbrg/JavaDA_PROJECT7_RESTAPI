package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.record.UserRecord;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
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

public class UserServiceTest {

    private static AutoCloseable mocks;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

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
    public void findAllUserOkTest() {
        User user = mock(User.class);

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserRecord.Vm.UserVm> result = userService.findAllUSer();

        assertEquals(List.of(new UserRecord.Vm.UserVm(user)), result);
    }

    @Test
    public void findAllUserReturnEmptyListTest() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserRecord.Vm.UserVm> result = userService.findAllUSer();

        assertNull(result);
    }

    @Test
    public void findUserByIdOkTest() {
        User user = mock(User.class);
        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserRecord.Vm.UserVm result = userService.findUserById(userId);

        assertEquals(new UserRecord.Vm.UserVm(user), result);
    }

    @Test
    public void findUserByIdReturnNullTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        UserRecord.Vm.UserVm result = userService.findUserById(1);

        assertNull(result);
    }

    @Test
    public void createUserOkTest() {
        User user = mock(User.class);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserRecord.Vm.UserVm result = userService.createUser(user);

        assertEquals(new UserRecord.Vm.UserVm(user), result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void createUserThrowErrorTest() {
        User user = mock(User.class);

        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.createUser(user));
    }

    @Test
    public void updateUserOkTest() {
        int userId = 1;
        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserRecord.Vm.UserVm result = userService.updateUser(userId, user);

        assertEquals(new UserRecord.Vm.UserVm(user), result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateUserNotFoundTest() {
        int userId = 1;
        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserRecord.Vm.UserVm result = userService.updateUser(userId, user);

        assertNull(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void deleteUserByIdOkTest() {
        int userId = 1;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void deleteUserByIdThrowErrorTest() {
        int userId = 1;

        doThrow(new RuntimeException()).when(userRepository).deleteById(userId);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
        verify(userRepository, times(1)).deleteById(userId);
    }
}
