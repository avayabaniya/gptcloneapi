package com.avayabaniya.gptclone.transactionId.helpers;


import com.avayabaniya.gptclone.transactionId.model.TransactionId;
import com.avayabaniya.gptclone.transactionId.repository.TransactionIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TransactionIdGenerator {

    private final TransactionIdRepository transactionIdRepository;

    @Autowired
    public TransactionIdGenerator(TransactionIdRepository transactionIdRepository) {
        this.transactionIdRepository = transactionIdRepository;
    }

    public String generate(int digits) {
        TransactionId transactionId = new TransactionId();
        String number = "";
        for (int i = 0; i < digits; i++) {
            int min = (i == 0) ? 1 : 0;
            number += ThreadLocalRandom.current().nextInt(min, 10);
        }

        TransactionId transactionIdRow = transactionId.createTransaction(number, transactionIdRepository);
        if (transactionIdRow.getTransactionId().isEmpty()) {
            this.generate(digits);
        }

        return transactionIdRow.getTransactionId();
    }

    public String generate(String prefix, int digits) {

        StringBuilder code = new StringBuilder(prefix);
        TransactionId transactionId = new TransactionId();
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> chars = new ArrayList<Character>();
        for (char c: alphabets.toCharArray()) {
            chars.add(c);
        }

        int totalChars = chars.size();

        for (int i = 1; i <= digits; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, totalChars -1);
            code.append(chars.get(index));
        }

        TransactionId transactionIdRow = transactionId.createTransaction(code.toString(), transactionIdRepository);
        if (transactionIdRow.getTransactionId().isEmpty()) {
            this.generate(prefix, digits);
        }

        return transactionIdRow.getTransactionId();
    }
}
