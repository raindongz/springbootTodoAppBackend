package com.cwmf.teaching.todoappservice.controller;

import com.cwmf.teaching.todoappservice.model.TodoItem;
import com.cwmf.teaching.todoappservice.model.TodoList;
import com.cwmf.teaching.todoappservice.model.User;
import com.cwmf.teaching.todoappservice.service.TodoListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/lists")
public class TodoListController {
    private final TodoListService service;

    private final static String USER_ID = "RUNDONG";

    //constructor
    public TodoListController(TodoListService service) {
        this.service = service;
    }


    @PostMapping
    public TodoList createTodoList( @RequestBody TodoList list){
        return this.service.createTodoList(USER_ID ,list);
    }

    @DeleteMapping("/{id}")
    public boolean deleteListById(@PathVariable String id){ return this.service.deleteTodolist(id); }

    //delete all lists
    @DeleteMapping
    public boolean deleteAllLists(){return this.service.deleteAllLists();}


    @GetMapping("/{id}")
    public Optional<TodoList> getListById(@PathVariable String id){ return this.service.getTodoListById(id); }

    //get all list method
    @GetMapping
    public List<TodoList> getAllLists(){ return this.service.getAllLists(); }

    @PutMapping
    public TodoList updatedList(@RequestBody TodoList list){
        return this.service.updateTodoList(USER_ID, list);
    }

    // real URL example:  POST /lists/{listId}/items?&itemId="itemId"

    @PostMapping("/{listId}/items/{itemId}") // add to the target list
    @ResponseBody
    public boolean moveItem(@PathVariable String listId, @PathVariable String itemId, @RequestParam("targetId") String targetId){ return this.service.moveItem(USER_ID, listId, itemId, targetId); }

    //CRUD items

    @PostMapping("/{listId}/items")
    public TodoItem createTodoItem(@PathVariable String listId, @RequestBody TodoItem item) { return this.service.createTodoItem(USER_ID, listId, item); }

    //Delete all items from a specific list
    @DeleteMapping("/{listId}/items")
    public boolean deleteAllItems(@PathVariable String listId){ return this.service.deleteAllItems(listId); }

    //Delete a specific item under specific list
    @DeleteMapping("/{listId}/items/{itemId}")
    public boolean deleteItem(@PathVariable String listId, @PathVariable String itemId ){ return this.service.deleteItem(USER_ID,listId,itemId); }

    //get item
    @GetMapping("/{listId}/items/{itemId}")
    public Optional<TodoItem> getItemById(@PathVariable String listId, @PathVariable String itemId) { return this.service.getTodoItemById(listId, itemId); }

    //get all items
    @GetMapping("/{listId}/items")
    public List<TodoItem> getAllItems(@PathVariable String listId){
        return this.service.getAllItems(listId);
    }
    //update item
    @PutMapping("/{listId}/items")
    public TodoItem updatedItem(@PathVariable String listId, @RequestBody TodoItem item) { return this.service.updateTodoItem(USER_ID, listId, item); }
}
