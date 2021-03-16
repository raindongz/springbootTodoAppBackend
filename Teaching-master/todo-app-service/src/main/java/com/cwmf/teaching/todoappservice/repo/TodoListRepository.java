package com.cwmf.teaching.todoappservice.repo;

import com.cwmf.teaching.todoappservice.model.TodoItem;
import com.cwmf.teaching.todoappservice.model.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends MongoRepository<TodoList, String>{

    TodoList findTodoListByListName(String listName);
    Optional<TodoList> findByListName(String listName);
    void deleteAllByUserId(String userId);
    //void deleteAllByItems(List<String> listId);
    //TodoItem findByItems(String itemId);
    //void deleteByItems(String itemId);

}
