/*
package com.cwmf.teaching.todoappservice.service;


import com.cwmf.teaching.todoappservice.model.TodoItem;
import com.cwmf.teaching.todoappservice.repo.TodoItemRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoItemService {

    private final TodoItemRepository repository;

    // dependency injection
    // autowired
    public TodoItemService(TodoItemRepository repository) {
        this.repository = repository;
    }

    //Create
    public TodoItem createTodoItem(String userId, String listId, TodoItem item) {
        if (StringUtils.isBlank(item.getId())) {
            TodoItem saved = repository.save(item);
            saved.setListId(listId);
            saved.setUserId(userId);
            return saved;
        } else {
            throw new IllegalArgumentException("Cannot create a new item comes with ID.");
        }
    }

    // Read
    public Optional<TodoItem> getTodoItemById(String id) {

        Optional<TodoItem> find = repository.findById(id);
        return find;
    }

    // Update
    public TodoItem updateTodoItem(TodoItem item) {
        if (StringUtils.isNotBlank(item.getId()) && repository.existsById(item.getId())) {
            TodoItem updated = repository.save(item);
            return updated;
        } else {
            throw new IllegalArgumentException("Cannot update item. Item with ID: = " + item.getId() + " doesn't exist.");
        }
    }


    // Delete
    public boolean deleteTodoItem(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("Cannot delete item. Item with ID= [" + id + "] doesn't exist.");
        }
    }
}
*/