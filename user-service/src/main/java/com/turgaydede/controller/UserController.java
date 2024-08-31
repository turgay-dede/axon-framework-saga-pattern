package com.turgaydede.controller;

import com.turgaydede.data.UserEntity;
import com.turgaydede.model.CreateUserRequest;
import com.turgaydede.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserEntity createUser(@RequestBody CreateUserRequest request) {
         return userService.createUser(request);
    }

}
