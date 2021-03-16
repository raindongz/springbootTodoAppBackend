package com.cwmf.teaching.todoappservice.service;

import com.cwmf.teaching.todoappservice.model.User;
import com.cwmf.teaching.todoappservice.repo.TodoItemRepository;
import com.cwmf.teaching.todoappservice.repo.TodoListRepository;
import com.cwmf.teaching.todoappservice.repo.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final TodoListRepository listRepository;
    //constructor
    public UserService(UserRepository repository, TodoListRepository listRepository) {
        this.repository = repository;
        this.listRepository = listRepository;
    }

    //create
    public User createUser(User user){
        User saved;
        //check if userId is empty, if yes continue check other properties
        if(StringUtils.isNotBlank((user.getUserId()))){
            throw new IllegalArgumentException("cannot create a new user comes with ID");
        }

            //check if front end pass in a user object with valid email
        if(StringUtils.isBlank(user.getEmail())){
            throw new IllegalArgumentException("email format not valid");
        }

        //check if this email has already been registered
        if(repository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("email has been registered");}

        //check password complexity
        if(isPasswordSafe(user.getPassword())){
            saved=repository.save(user);
            return saved;
        }else{
            throw new IllegalArgumentException("password too simple");
        }
    }

    //password complexity check helper method
    private boolean isPasswordSafe(String passWord){
        if(passWord.matches("(?=.*[0-9]).*") && passWord.matches("(?=.*[a-z]).*") && passWord.matches("(?=.*[A-Z]).*") && passWord.matches("(?=.*[~!@#$%^&*()_-]).*")){
            return true;
        }else{
            return false;
        }
    }

    //Read user by id
    public Optional<User> getUserById(String id){
        Optional<User> find=repository.findById(id);
        return find;
    }
    //read user by email
    public List<User> getUserByEmail(String email) {
        Optional<User> userOptional = repository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User with email " + email + "doesn't exist"));
        return Collections.singletonList(user);
    }
    //read all user
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    //Update
    public User updateUser(User user){
        User updated;
        if(StringUtils.isBlank(user.getUserId()) || (!repository.existsById(user.getUserId()))){
            throw new IllegalArgumentException("cannot update user. user with ID:=" + user.getUserId()+" doesn't exist");
        }
        if(StringUtils.isBlank(user.getEmail())){
            throw new IllegalArgumentException("email format not valid");
        }
        //check password complexity
        if(isPasswordSafe(user.getPassword())){
            updated=repository.save(user);
            return updated;
        }else{
            throw new IllegalArgumentException("password to simple");
        }
    }


    //Delete user by id
    public boolean deleteUser(String id){
        if(repository.existsById(id)){
            deleteAllLists(id);
            repository.deleteById(id);
            return true;
        }
        else{
            throw new IllegalArgumentException("Cannot delete user." + id+" doesn't exist");
        }
    }

  //Delete user by email
    public boolean deleteUserByEmail(String email){
        if(repository.findByEmail(email).isPresent()){
            repository.deleteByEmail(email);
            return true;
        }
        else {
            throw new IllegalArgumentException("user with such email doesn't exist");
        }
    }

    //Delete all Lists

    public boolean deleteAllLists(String id){
        if(repository.existsById(id)){
              listRepository.deleteAllByUserId(id);
              return true;
        }else{
            throw new IllegalArgumentException("user doens't exist");
        }
    }


    public List<User> getUsers(String email) {
        if(StringUtils.isBlank(email)) {
            return this.getAllUsers();
        }
        else {
            return getUserByEmail(email);
        }
    }
}
