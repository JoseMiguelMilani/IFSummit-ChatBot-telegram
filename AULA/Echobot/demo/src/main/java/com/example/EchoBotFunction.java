package com.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

//local onde as respostas são processadas e enviadas ao usuario

public class EchoBotFunction implements LongPollingSingleThreadUpdateConsumer {
    
    private static final TelegramClient bot = new OkHttpTelegramClient("8906118624:AAE3TIamfmUqJY4-DxbiHTFIaICU7bI3hSQ");

    public void consume(Update update){

        if (update.hasMessage() && update.getMessage().hasText()) {
            
            Message mensagem = update.getMessage();

            String Resposta = mensagem.getText();

            SendMessage botmensagem = SendMessage
                .builder()
                .chatId(mensagem.getChatId())
                .text(Resposta)
                .build();

            try {
                bot.execute(botmensagem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
