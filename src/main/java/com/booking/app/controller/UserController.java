package com.booking.app.controller;

//import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final String API_NAME = "user";

    @GetMapping(UserController.API_NAME + "/{id}")
    public ResponseEntity<String> getUser(@PathVariable long id) {
        return ResponseEntity.ok("user1");
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok("All users!");
    }


    @PostMapping(UserController.API_NAME)
    public ResponseEntity<String> createNewUser() {
        return ResponseEntity.ok("new user");
    }

    @PutMapping(UserController.API_NAME + "/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id){
        return new ResponseEntity<>(String.format("User " + id, " has been updated!"), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(UserController.API_NAME + "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        return new ResponseEntity<>(String.format("User " + id, " has been deleted!"), HttpStatus.ACCEPTED);

    }


}

