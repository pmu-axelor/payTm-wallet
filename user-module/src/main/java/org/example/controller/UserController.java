package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.example.dto.UserResponseDto;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUserAccount(@RequestBody @Valid User user){
        try {
            return new ResponseEntity<>(userService.createUserAccount(user), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<Boolean> checkUserExistance(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.checkUserExistance(id),HttpStatus.OK);
    }
}
