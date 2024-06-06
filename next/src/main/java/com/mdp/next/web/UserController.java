package com.mdp.next.web;

import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.mdp.next.service.UserService;
import com.mdp.next.entity.*;
import com.mdp.next.exception.ErrorResponse;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.ArrayList;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userID) {
        return new ResponseEntity<>(userService.getUser(userID), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage())); 
            ErrorResponse error = new ErrorResponse(errors);  
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{userID}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, BindingResult result, @PathVariable Long userID) {

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage())); 
            ErrorResponse error = new ErrorResponse(errors);  
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(user.getEmail(), user.getAddress(), userID);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
