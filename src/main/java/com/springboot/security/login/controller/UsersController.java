package com.springboot.security.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.security.login.dto.request.UserLoginRequest;
import com.springboot.security.login.model.Users;
import com.springboot.security.login.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class UsersController {
    private static Logger logger = Logger.getLogger(UsersController.class.getName());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    public PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ObjectMapper objMapper;

    @PostMapping("/register")
    public ObjectNode register(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        Users user = new Users();
        user.setUsername(userLoginRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userLoginRequest.getPassword()));
        user.setEnabled(true);
        usersRepository.save(user);

        ObjectNode objectNode = objMapper.createObjectNode();
        objectNode.put("status", "SUCCESS");
        return objectNode;
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
