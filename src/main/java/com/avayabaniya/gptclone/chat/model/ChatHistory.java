package com.avayabaniya.gptclone.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "chat_histories")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(name = "sent_message", columnDefinition = "text")
    private String sentMessage;

    @Column(name = "received_message", columnDefinition = "text")
    private String receivedMessage;

    @Column(columnDefinition = "text")
    private String request;

    @Column(columnDefinition = "text")
    private String response;

    @Column(name = "response_status")
    private String responseStatus;

    @Column(name = "response_id")
    private String responseId;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void prePersist(){
        if (this.createdAt == null) this.createdAt = new Date();
        if (this.updatedAt ==null) this.updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate(){
        this.updatedAt = new Date();
    }

}
