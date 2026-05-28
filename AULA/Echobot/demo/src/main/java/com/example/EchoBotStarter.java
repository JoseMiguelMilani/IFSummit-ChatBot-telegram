package com.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

//arquivo onde iremos registar o bot

public class EchoBotStarter {

    
    
    public static void echoStart(){

    String token = "8906118624:AAE3TIamfmUqJY4-DxbiHTFIaICU7bI3hSQ";

        try{
            TelegramBotsLongPollingApplication botTelegram = new TelegramBotsLongPollingApplication();
            botTelegram.registerBot(token,new EchoBotFunction());
            System.out.println("bot iniciado");

            Thread.currentThread().join();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
