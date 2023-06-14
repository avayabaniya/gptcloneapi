package com.avayabaniya.gptclone.chat.dao;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.chat.model.Chat;
import com.avayabaniya.gptclone.chat.repository.ChatRepository;
import com.avayabaniya.gptclone.chat.request.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ChatDao {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatDao(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(ChatRequest chatRequest, ApiUser user, Chat chat) {

        if (chat.getChatId() != null) {
            return chat;
        }

        String message = chatRequest.getChatMessage();
        chat.setChatId(UUID.randomUUID().toString());
        chat.setTitle(message.length() > 30 ? message.substring(0, 30) : message);
        chat.setUser(user);

        return this.chatRepository.save(chat);
    }

    public Optional<Chat> findChatByChatIdAndUser(String chatId, ApiUser user) {
        return this.chatRepository.findByChatIdAndUser(chatId, user);
    }

    public List<Chat> findAllChatByUser(ApiUser user) {
        return this.chatRepository.findAllByUser(user);
    }
}
