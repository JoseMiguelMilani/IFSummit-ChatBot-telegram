package com.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

class SortearBotStarter{

    public static void Start(){
        String token = " --seuToken-- ";
        
        try {
            TelegramBotsLongPollingApplication botTelegramIfpr = new TelegramBotsLongPollingApplication();
            botTelegramIfpr.registerBot(token , new SortearBotFunction());
            System.out.println("bot de sorteio Iniciado");

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}