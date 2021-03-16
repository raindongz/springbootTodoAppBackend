package com.cwmf.teaching.todoappservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String userId;

    private String name;
    private String email;
    private String password;

    private List<String> listId;
    private List<String> itemsId;
    @CreatedDate
    private Date createdDate;

    public void addList(){

    }
}
