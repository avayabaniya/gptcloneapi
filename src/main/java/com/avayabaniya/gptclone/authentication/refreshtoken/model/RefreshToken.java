package com.avayabaniya.gptclone.authentication.refreshtoken.model;

import com.avayabaniya.gptclone.authentication.ApiUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "api_user_username",
            referencedColumnName = "username",
            foreignKey = @ForeignKey(
                    name = "api_user_username"
            )
    )
    private ApiUser apiUser;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

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
