package com.avayabaniya.gptclone.chat.repository;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.chat.model.Chat;
import com.avayabaniya.gptclone.chat.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

   //@Query(value = "select * from chat_histories where chat_id = ?1", nativeQuery = true)
   List<ChatHistory> findAllByChat(Chat chat);

   @Query(value = "select * from chat_histories where chat_id = ?1 order by created_at desc LIMIT ?2, ?3", nativeQuery = true)
   List<ChatHistory> findAllByChatIdWithLimit(Chat chat, int skip, int limit);

   int countByChat(Chat chat);


}
