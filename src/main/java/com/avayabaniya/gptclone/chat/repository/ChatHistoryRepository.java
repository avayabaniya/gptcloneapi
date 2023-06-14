package com.avayabaniya.gptclone.chat.repository;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.chat.model.Chat;
import com.avayabaniya.gptclone.chat.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

   List<ChatHistory> findAllByChat(Chat chat);
}
