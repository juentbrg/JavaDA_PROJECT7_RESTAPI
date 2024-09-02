package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.record.UserRecord;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserRecord.Vm.UserVm> findAllUSer() {
        List<User> userList = userRepository.findAll();

        if (userList.isEmpty())
            return null;

        return userList
                .stream()
                .map(UserRecord.Vm.UserVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserRecord.Vm.UserVm findUserById(int id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent())
            return new UserRecord.Vm.UserVm(userOpt.get());

        return null;
    }

    @Transactional
    public UserRecord.Vm.UserVm createUser(UserRecord.Api.UserRequest userRequest) {
        try {
            User user = new User(userRequest);
            userRepository.save(user);
            return new UserRecord.Vm.UserVm(user);
        } catch (Exception e) {
            throw new RuntimeException("failed to create user", e);
        }
    }

    @Transactional
    public UserRecord.Vm.UserVm updateUser(int id, UserRecord.Api.UserRequest userRequest) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            BeanUtils.copyProperties(userRequest, user, getNullPropertyNames(userRequest));
            userRepository.save(user);
            return new UserRecord.Vm.UserVm(user);
        }

        return null;
    }

    @Transactional
    public void deleteUser(int id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("failed to delete user", e);
        }
    }
}
