package com.avayabaniya.gptclone.chat.api;

import com.avayabaniya.gptclone.chat.api.request.OpenApiChatRequest;
import com.avayabaniya.gptclone.chat.api.request.OpenApiRequestMessage;
import com.avayabaniya.gptclone.chat.model.ChatHistory;
import com.avayabaniya.gptclone.chat.request.ChatRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OpenApiRequestBuilder {

    List<ChatHistory> chatHistories;

    private final ChatRequest chatRequest;

    private final boolean includePreviousChat;

    //without previous history
    public OpenApiRequestBuilder(ChatRequest chatRequest) {
        this.chatRequest = chatRequest;
        this.includePreviousChat = false;
    }

    //with previous history
    public OpenApiRequestBuilder(ChatRequest chatRequest, List<ChatHistory> chatHistories) {
        this.chatRequest = chatRequest;
        this.chatHistories = chatHistories;
        this.includePreviousChat = true;
    }



    public String getJsonRequest() {

        //Add previous conversation messages
        List<OpenApiRequestMessage> messages = new ArrayList<>();
        if (this.includePreviousChat && this.chatHistories != null && this.chatHistories.size() > 0) {
            this.chatHistories
                    .forEach((history) -> {
                        if (history.getSentMessage() != null) {
                            messages.add(new OpenApiRequestMessage("user", history.getSentMessage()));
                        }

                        if (history.getReceivedMessage() != null) {
                            messages.add(new OpenApiRequestMessage("assistant", history.getReceivedMessage()));
                        }
                    });
        }

        //Add latest conversation messages
        messages.add(new OpenApiRequestMessage("user", this.chatRequest.getChatMessage()));

        OpenApiChatRequest apiRequest = new OpenApiChatRequest(messages);

        Gson gson = new Gson();
        return gson.toJson(apiRequest);

    }
}
