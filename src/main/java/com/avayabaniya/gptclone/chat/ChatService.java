package com.avayabaniya.gptclone.chat;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.authentication.AuthUserDetails;
import com.avayabaniya.gptclone.chat.api.OpenApiClient;
import com.avayabaniya.gptclone.chat.api.OpenApiRequestBuilder;
import com.avayabaniya.gptclone.chat.api.response.OpenApiChatResponse;
import com.avayabaniya.gptclone.chat.dao.ChatDao;
import com.avayabaniya.gptclone.chat.dao.ChatHistoryDao;
import com.avayabaniya.gptclone.chat.model.Chat;
import com.avayabaniya.gptclone.chat.model.ChatHistory;
import com.avayabaniya.gptclone.chat.request.ChatRequest;
import com.avayabaniya.gptclone.chat.response.ChatListResponse;
import com.avayabaniya.gptclone.chat.response.ChatResponse;
import com.avayabaniya.gptclone.config.AppPropertiesConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatDao chatDao;

    private final ChatHistoryDao chatHistoryDao;

    private final AppPropertiesConfig config;

    @Autowired
    public ChatService(ChatDao chatDao, ChatHistoryDao chatHistoryDao, AppPropertiesConfig config) {
        this.chatDao = chatDao;
        this.chatHistoryDao = chatHistoryDao;
        this.config = config;
    }

    public ChatResponse createNewChatMessage(ChatRequest chatRequest) {

        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApiUser user = authUserDetails.getApiUser();

        //get existing chat if valid chat id, else create new chat
        Chat chat = chatRequest.getChatId() == null
                ? this.chatDao.createChat(chatRequest, user, new Chat())
                : this.chatDao.findChatByChatIdAndUser(chatRequest.getChatId(), user)
                        .orElseThrow(() -> new RuntimeException("Invalid chat id"));


        //get chat histories
        List<ChatHistory> chatHistories = chat.getChatHistories();


        //prepare request
        OpenApiRequestBuilder requestBuilder = new OpenApiRequestBuilder(chatRequest, chatHistories);
        String jsonRequest = requestBuilder.getJsonRequest();

        System.out.println(jsonRequest);

        //save in chat history before request
        ChatHistory chatHistory = this.chatHistoryDao.createChatHistory(chatRequest, chat, jsonRequest, new ChatHistory());

        //send request to open api
        String url = this.config.getOpenApiUrl();
        String clientToken = this.config.getOpenApiClientId();
        OpenApiClient client = new OpenApiClient();
        String response = client.makeRequest(jsonRequest, url, clientToken);

        System.out.println(response);
        response = response.replace("\\\\n", "");
        System.out.println(response);


        Gson gson = new Gson();
        System.out.println(response);
        OpenApiChatResponse apiResponse = gson.fromJson(response, OpenApiChatResponse.class);

        //update response
        chatHistory = this.chatHistoryDao.updateChatHistory(chatHistory, apiResponse, response);

        return new ChatResponse(chatHistory);
    }

    public List<ChatListResponse> getChatList() {
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApiUser user = authUserDetails.getApiUser();

        return this.chatDao.findAllChatByUser(user)
                .stream()
                .map(ChatListResponse::new)
                .collect(Collectors.toList());
    }
}
