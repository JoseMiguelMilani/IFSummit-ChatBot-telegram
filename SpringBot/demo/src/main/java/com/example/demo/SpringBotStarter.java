package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class SpringBotStarter{

    @Value("${botToken}")
    private String botToken;

    private final SpringBotFunction springBotFunction;

    public void Start(){
        String token = botToken;
        
        try {
            TelegramBotsLongPollingApplication botTelegramIfpr = new TelegramBotsLongPollingApplication();
            botTelegramIfpr.registerBot(token , springBotFunction);
            System.out.println("bot Iniciado");

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}