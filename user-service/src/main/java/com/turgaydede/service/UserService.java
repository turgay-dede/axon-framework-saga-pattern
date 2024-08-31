package com.turgaydede.service;

import com.turgaydede.data.UserEntity;
import com.turgaydede.model.CreateUserRequest;

public interface UserService {
    public UserEntity createUser(CreateUserRequest dto);
}
