package com.avayabaniya.gptclone.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "api_users")
public class ApiUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String role;

    private String picture;

    private String name;

    @Column(name = "auth_type")
    private String authType;

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
