package com.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

class EchoBotStarter{

    public static void EchoStart(){
        String token = " --seuToken-- ";
        
        try {
            TelegramBotsLongPollingApplication botTelegramIfpr = new TelegramBotsLongPollingApplication();
            botTelegramIfpr.registerBot(token , new EchoBotFunction());
            System.out.println("bot echo Iniciado");

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}