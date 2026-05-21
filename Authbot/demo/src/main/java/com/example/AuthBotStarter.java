package com.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

class AuthBotStarter{

    public static void Start(){
        String token = " --seuToken-- ";
        
        try {
            TelegramBotsLongPollingApplication botTelegramIfpr = new TelegramBotsLongPollingApplication();
            botTelegramIfpr.registerBot(token , new AuthBotFunction());
            System.out.println("bot Iniciado");

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}