package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class ChatMessage {
    
    private String texto;
    private long chatId;

    public ChatMessage(){}
    
    public ChatMessage(String texto, long chatId){
        this.texto = texto;
        this.chatId = chatId;
    }
}
