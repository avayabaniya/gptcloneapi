package com.avayabaniya.gptclone.transactionId.model;



import com.avayabaniya.gptclone.transactionId.repository.TransactionIdRepository;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "transaction_ids")
public class TransactionId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "transaction_id", unique = true)
    private String TransactionId;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    protected void prePersist(){
        if (this.createdAt == null) this.createdAt = new Date();
    }

    public TransactionId createTransaction(String transactionId, TransactionIdRepository repository) {
        try {
            this.setTransactionId(transactionId);
            return repository.save(this);

        } catch (Exception exception) {
            return this;
        }
    }
}
