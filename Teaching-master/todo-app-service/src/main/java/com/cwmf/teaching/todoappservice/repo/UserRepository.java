package com.cwmf.teaching.todoappservice.repo;

import com.cwmf.teaching.todoappservice.model.TodoItem;
import com.cwmf.teaching.todoappservice.model.TodoList;
import com.cwmf.teaching.todoappservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    //void deleteByUserId(List<TodoList> id);
    //void deleteByUserId(List<TodoItem> id);
    //void deleteByUserIdOrEmail(String id, String email);
}
