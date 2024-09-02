package com.nnk.springboot.record;

import com.nnk.springboot.domain.User;

public interface UserRecord {

    interface Vm {
        record UserVm(
                String username,
                String fullname,
                String role
        ){
            public UserVm(User user) {
                this(
                        user.getUsername(),
                        user.getFullname(),
                        user.getRole()
                );
            }
        }

    }

    interface Api {
        record UserRequest(
                String username,
                String fullname,
                String password
        ){}
    }
}
