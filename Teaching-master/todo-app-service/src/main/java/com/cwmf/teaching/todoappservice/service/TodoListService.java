package com.cwmf.teaching.todoappservice.service;

import com.cwmf.teaching.todoappservice.model.TodoItem;
import com.cwmf.teaching.todoappservice.model.TodoList;
import com.cwmf.teaching.todoappservice.repo.TodoItemRepository;
import com.cwmf.teaching.todoappservice.repo.TodoListRepository;
import com.cwmf.teaching.todoappservice.repo.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {
    private final TodoListRepository listRepository;
    private final TodoItemRepository itemRepository;
    public TodoListService(TodoListRepository listRepository, TodoItemRepository itemRepository) {
        this.listRepository = listRepository;
        this.itemRepository = itemRepository;
    }

    //create
    public TodoList createTodoList(String userId, TodoList list) {
        if(StringUtils.isNotBlank(list.getListId())) {
            throw new IllegalArgumentException("cannot create a new item come with ID");
        }

        //check if assigned with userId
        if(StringUtils.isBlank(list.getUserId())){
            throw new IllegalArgumentException("List must contain userId ");
        }

        //check if list come with name
        if(StringUtils.isBlank(list.getListName())){
            throw new IllegalArgumentException("list must come with a name");
        }
        //check if there is list come with same name
        if(listRepository.findTodoListByListName(list.getListName()) == null){
            list.setItems(new ArrayList<>());
            list.setUserId(userId);
            list.setListId(null);
            TodoList saved =listRepository.save(list);
            return saved;
        }else{
            throw new IllegalArgumentException("list name already exist");
        }


    }

    //Read
    public Optional<TodoList> getTodoListById (String id){
        if(!listRepository.existsById(id)){
            throw new IllegalArgumentException("list with ID "+id+" doesn't exist");
        }
        Optional<TodoList> find= listRepository.findById(id);
        return find;
    }
    //read all lists
    public List<TodoList> getAllLists(){
       return listRepository.findAll();
    }

    //update
    public TodoList updateTodoList(String USER_ID, TodoList list){
        if(StringUtils.isBlank(list.getListId()) || (!listRepository.existsById(list.getListId())) ){
            throw new IllegalArgumentException("cannot update list. List with ID: =" + list.getListId()+ "doesn't exist");
        }
        //check if user id is provided
        if(StringUtils.isBlank(list.getUserId())){
            throw new IllegalArgumentException("List must contain userId ");
        }
        //check if userID is right
        if(!list.getUserId().equals(USER_ID)){
            throw new IllegalArgumentException("wrong userID provided");
        }
        //check if list come with name
        if(StringUtils.isBlank(list.getListName())) {
            throw new IllegalArgumentException("list must come with a name");
        }
        //check if there is list come with same name
        if(listRepository.findTodoListByListName(list.getListName())==null){
            //make sure the items in old list get stored
            TodoList oldList;
            oldList=listRepository.findById(list.getListId()).get();
            list.setItems(oldList.getItems());
            list.setUserId(USER_ID);
            TodoList updated =listRepository.save(list);
            return updated;
        }else{
            throw new IllegalArgumentException("list name already exist");
        }
    }

    //move
    public boolean moveItem(String userId, String listId, String itemId, String targetId){
        TodoList currentList;
        TodoList targetList;
        TodoItem updatedItem;
        //check if current list provided exist
        if(!getTodoListById(listId).isPresent()){
            throw new IllegalArgumentException("list ID "+ listId+" doesn't exist");
        }
        //check if target list exist
        if(!getTodoListById(targetId).isPresent()){
            throw new IllegalArgumentException("targetlist" + listId +"doesnt exist");
        }
        // 1. check if itemID exists in repository and in provided list
        if(!itemRepository.existsById(itemId) && listRepository.findById(listId).get().getItems().contains(itemId)){
            throw new IllegalArgumentException("item with ID: "+itemId+" doesn't exist");
        }
        //check if itemID is already in target List(in case user call multiple time post method )
        if(listRepository.findById(targetId).get().getItems().contains(itemId)){
            throw new IllegalArgumentException("item with ID "+itemId+" already exist in target list");
        }
        // 2. get item
        // 3. add this item to new list
        targetList=listRepository.findById(targetId).get();
        targetList.getItems().add(itemId);
        listRepository.save(targetList);
        // 4. remove this item from old list
        currentList=listRepository.findById(listId).get();
        currentList.getItems().remove(itemId);
        listRepository.save(currentList);
        // 4.1. change the listID
        updatedItem=itemRepository.findById(itemId).get();
        updatedItem.setListId(targetId);
        updateTodoItem(userId, targetId, updatedItem);
        return true;
    }

    //Delete List
    public boolean deleteTodolist(String id){
        if(listRepository.existsById(id)){
            deleteAllItems(id);
            listRepository.deleteById(id);
            return true;
        }
        else{
            throw new IllegalArgumentException("cannot delete item. item with ID ="+ id +"doesn't exist");
        }
    }
    //delete all Lists
    public boolean deleteAllLists(){
        listRepository.deleteAll();
        itemRepository.deleteAll();
        return true;
    }

    //Delete all item in specific List
    public boolean deleteAllItems(String listId){

        //check if specific list is exist
        if(listRepository.existsById(listId)){
            itemRepository.deleteAllByListId(listId);
            //remove all Item id correspond to this List
            TodoList updatedList=listRepository.findById(listId).get();
            updatedList.getItems().clear();
            listRepository.save(updatedList);
            return true;
        }
        else{
            throw new IllegalArgumentException("List ID" + listId +"doesn't exist");
        }
    }



    //item CRUD

    //Create
    public TodoItem createTodoItem(String userId, String listId, TodoItem item) {
        if (StringUtils.isBlank(item.getId())) {
            item.setListId(listId);
            item.setUserId(userId);
            TodoList todo=listRepository.findById(listId).get();
            //note: save item first so that there will be an item id to be able added to list
            //if assigned id first, the item id will become the id that user provided
            //set id to null so that request wont return an empty id
            item.setId(null);
            TodoItem saved = itemRepository.save(item);
            todo.getItems().add(saved.getId());
            listRepository.save(todo);
            return saved;
        } else {
            throw new IllegalArgumentException("Cannot create a new item comes with ID.");
        }
    }

    //get todo item
    public Optional<TodoItem> getTodoItemById(String listId, String itemId) {
        if(!listRepository.findById(listId).isPresent()){
            throw new IllegalArgumentException("list ID "+listId+" doesn't exist");
        }
        //check if this list contains that item
        if(!listRepository.findById(listId).get().getItems().contains(itemId)){
            throw new IllegalArgumentException("item ID "+itemId+"not exist in that list");
        }
        //check if item exist
        if(!itemRepository.existsById(itemId)){
            throw new IllegalArgumentException("item ID "+ itemId+" doesn't exist");
        }
        Optional<TodoItem> find = itemRepository.findById(itemId);
        return find;
    }
    //get all items under specific list
    public List<TodoItem> getAllItems(String listId){
       if(!listRepository.existsById(listId)){
           throw new IllegalArgumentException("list with ID " +listId+" doesn't exist!");
       }
       List<TodoItem> items;
       items=itemRepository.findAllByListId(listId);
    return items;
    }

    // Update
    public TodoItem updateTodoItem(String userId,String listId, TodoItem item) {
        //check if provided list exist
        if(!listRepository.existsById(listId)){
            throw new IllegalArgumentException("list with ID "+listId+ " doesn't exist");
        }
        //check if item come with valid ID
        if(StringUtils.isBlank(item.getId())){
            throw new IllegalArgumentException("item ID can not be blank");
        }
        //check if this item exist in provided list
        if(!listRepository.findById(listId).get().getItems().contains(item.getId())){
            throw new IllegalArgumentException("item with ID "+ item.getId()+ " doesn't exist in specified list");
        }

        if (itemRepository.existsById(item.getId())) {
            item.setListId(listId);
            item.setUserId(userId);
            TodoItem updated = itemRepository.save(item);
            return updated;
        } else {
            throw new IllegalArgumentException("Cannot update item. Item with ID: = " + item.getId() + " doesn't exist.");
        }
    }
    //Delete Item
    public boolean deleteItem(String userId ,String listId, String itemId){
        //check if this list exist
        if(!listRepository.existsById(listId)){
            throw new IllegalArgumentException("list ID "+listId +"doesn't exist");
        }
        //check if itemId exist in that list
        if(!listRepository.findById(listId).get().getItems().contains(itemId)){
            throw new IllegalArgumentException("itemId "+ itemId+" not found in that list");
        }
        //check if item exist in repository
        if(itemRepository.existsById(itemId)){
            itemRepository.deleteById(itemId);
            //delete itemId stored in that List
            TodoList updatedlist= listRepository.findById(listId).get();
            updatedlist.getItems().remove(itemId);
            listRepository.save(updatedlist);
            return true;
        }else{
            throw new IllegalArgumentException("No such item exist in that List");
        }

    }
}

