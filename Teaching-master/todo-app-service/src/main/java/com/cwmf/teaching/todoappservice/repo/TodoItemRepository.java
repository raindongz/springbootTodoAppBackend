package com.cwmf.teaching.todoappservice.repo;

import com.cwmf.teaching.todoappservice.model.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TodoItemRepository extends MongoRepository<TodoItem, String> {

   // TodoItem findTodoItemByUserIdIs(String userId);
   // List<TodoItem> findAllByCompleted(boolean completed);
    void deleteAllByListId(String listId);
    List<TodoItem> findAllByListId(String listId);
    //void deleteByUserId(String userId);


}
