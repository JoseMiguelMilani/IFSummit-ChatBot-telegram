package com.example;

import java.util.Random;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SortearBotFunction implements LongPollingSingleThreadUpdateConsumer{

    private static final TelegramClient bot = new OkHttpTelegramClient(" --seuToken-- "); 

    public void consume(Update update) {  

        if (update.hasMessage() && update.getMessage().hasText()) {  
            Message mensagem = update.getMessage(); 


            String resposta = definirResposta(mensagem);

            SendMessage botmessage = SendMessage
                .builder()
                .chatId(mensagem.getChatId())
                .text(resposta)
                .build();
            try {
                bot.execute(botmessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String definirResposta(Message mensagem){
        String TextoRecebido = mensagem.getText();
        String TextoMandar = "";

        Random geradorAleatorio = new Random();


        switch (TextoRecebido) {
            case "/sortear":
                TextoMandar = "numero sorteado: "+(geradorAleatorio.nextInt(11));
                break;
        
            default:
                TextoMandar = "Não é um comando";
                break;
        }

        return TextoMandar;
    }

}