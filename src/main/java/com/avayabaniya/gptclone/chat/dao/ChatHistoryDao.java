package com.avayabaniya.gptclone.chat.dao;

import com.avayabaniya.gptclone.chat.api.response.OpenApiChatResponse;
import com.avayabaniya.gptclone.chat.model.Chat;
import com.avayabaniya.gptclone.chat.model.ChatHistory;
import com.avayabaniya.gptclone.chat.repository.ChatHistoryRepository;
import com.avayabaniya.gptclone.chat.request.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatHistoryDao {

    private final ChatHistoryRepository chatHistoryRepository;

    @Autowired
    public ChatHistoryDao(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
    }

    public ChatHistory createChatHistory(ChatRequest chatRequest, Chat chat, String jsonRequest, ChatHistory chatHistory) {

        chatHistory.setChat(chat);
        chatHistory.setSentMessage(chatRequest.getChatMessage());
        chatHistory.setRequest(jsonRequest);

        return this.chatHistoryRepository.save(chatHistory);
    }

    public ChatHistory updateChatHistory(ChatHistory chatHistory, OpenApiChatResponse response, String jsonResponse) {

        chatHistory.setResponse(jsonResponse);
        chatHistory.setReceivedMessage(response.getChoices().get(0).getMessage().getContent());
        chatHistory.setResponseId(response.getId());
        chatHistory.setResponseStatus("1");

        return this.chatHistoryRepository.save(chatHistory);
    }


    public List<ChatHistory> findAllChatHistory(Chat chat, int skip, int limit) {
        //return this.chatHistoryRepository.findAllByChat(chat);
        return this.chatHistoryRepository.findAllByChatIdWithLimit(chat, skip, limit);
    }

    public int countChatHistory(Chat chat) {
        return this.chatHistoryRepository.countByChat(chat);
    }
}
