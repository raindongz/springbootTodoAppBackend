package com.cwmf.teaching.todoappservice.controller;

import com.cwmf.teaching.todoappservice.model.User;
import com.cwmf.teaching.todoappservice.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    //constructor
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return this.service.createUser(user);
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUserById(@PathVariable String userId){
        return this.service.deleteUser(userId);
    }

    //delete userby email
    @DeleteMapping
    public boolean deleteUserByEmail(@RequestParam("email") String email){return this.service.deleteUserByEmail(email); }

    //delete all lists under the userId
    @DeleteMapping("/{userId}/list")
    public boolean deleteAllListsById(@PathVariable String userId){return this.service.deleteAllLists(userId);}

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id){
        return this.service.getUserById(id);
    }

    //get user by email address
    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String email){return this.service.getUsers(email); }

    //get all users
    //@GetMapping
    //public List<User> getAllUsers(){return this.service.getAllUsers();}

    @PutMapping
    public User updateUser(@RequestBody User user){
        return this.service.updateUser(user);
    }

}
