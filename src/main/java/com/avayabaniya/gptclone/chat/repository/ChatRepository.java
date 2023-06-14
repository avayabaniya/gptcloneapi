package com.avayabaniya.gptclone.chat.repository;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByChatId(String chatId);

    Optional<Chat> findByChatIdAndUser(String chatId, ApiUser user);

    List<Chat> findAllByUser(ApiUser user);
}
