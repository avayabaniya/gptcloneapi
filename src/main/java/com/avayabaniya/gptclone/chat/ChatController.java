package com.avayabaniya.gptclone.chat;

import com.avayabaniya.gptclone.chat.model.Chat;
import com.avayabaniya.gptclone.chat.request.ChatRequest;
import com.avayabaniya.gptclone.chat.response.ChatListResponse;
import com.avayabaniya.gptclone.chat.response.ChatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/message")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ChatResponse> chatMessage(@RequestBody ChatRequest chatRequest) {
        ChatResponse response = this.chatService.createNewChatMessage(chatRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/list")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<ChatListResponse>> chatList() {
        List<ChatListResponse> response = this.chatService.getChatList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
