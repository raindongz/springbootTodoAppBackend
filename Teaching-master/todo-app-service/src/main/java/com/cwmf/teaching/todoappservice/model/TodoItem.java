package com.cwmf.teaching.todoappservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoItem {

    @Id
    private String id;

    private String content;

    private String listId;

    private String userId;

    private boolean completed;

    //private Date targetDate;
    //private Date completedDate;

//    @CreatedBy
//    private String createdBy;

    @CreatedDate
    private Date createdDate;

//    @LastModifiedBy
//    private String lastModifiedBy;

    @LastModifiedDate
    private Date lastModifiedDate;
}
