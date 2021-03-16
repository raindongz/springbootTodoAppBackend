package com.cwmf.teaching.todoappservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoList {
    @Id
    private String listId;

    private String userId;
    private String listName;
    private List<String> items;

    //check if whole list is completed,
    //private boolean completed;
}
