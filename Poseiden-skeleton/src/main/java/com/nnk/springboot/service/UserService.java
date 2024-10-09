package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.record.UserRecord;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserRecord.Vm.UserVm> findAllUSer() {
        logger.info("Fetching all users");
        List<User> userList = userRepository.findAll();
        logger.debug("Found {} users", userList.size());

        if (userList.isEmpty()) {
            logger.warn("No users found");
            return null;
        }

        return userList
                .stream()
                .map(UserRecord.Vm.UserVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserRecord.Vm.UserVm findUserById(int id) {
        logger.info("Fetching user with id: {}", id);
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            logger.debug("User found with id: {}", id);
            return new UserRecord.Vm.UserVm(userOpt.get());
        }

        logger.warn("No user found with id: {}", id);
        return null;
    }

    @Transactional
    public UserRecord.Vm.UserVm createUser(UserRecord.Api.UserRequest userRequest) {
        try {
            logger.info("Creating new user");
            User user = new User(userRequest);
            userRepository.save(user);
            logger.debug("User created with id: {}", user.getId());
            return new UserRecord.Vm.UserVm(user);
        } catch (Exception e) {
            logger.error("Failed to create user", e);
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Transactional
    public UserRecord.Vm.UserVm updateUser(int id, UserRecord.Api.UserRequest userRequest) {
        logger.info("Updating user with id: {}", id);
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            BeanUtils.copyProperties(userRequest, user, getNullPropertyNames(userRequest));
            userRepository.save(user);
            logger.debug("User updated with id: {}", id);
            return new UserRecord.Vm.UserVm(user);
        }

        logger.warn("No user found with id: {} for update", id);
        return null;
    }

    @Transactional
    public void deleteUser(int id) {
        try {
            logger.info("Deleting user with id: {}", id);
            userRepository.deleteById(id);
            logger.debug("User deleted with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete user with id: {}", id, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}
