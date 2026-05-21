package com.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class EchoBotFunction implements LongPollingSingleThreadUpdateConsumer{  //implementa a função de ouvir o servidor Telegram

    private static final TelegramClient bot = new OkHttpTelegramClient(" --seuToken-- "); // cria um objeto do bot que serve para gerenciar requisições

    public void consume(Update update) {  //essa função sera acionada toda vez que o chat Telegram receber uma nova mensagem

        if (update.hasMessage() && update.getMessage().hasText()) {  //verifica se o usuario mandou um texto

            Message mensagem = update.getMessage();  //extrai as informações contidas na mensagem do usuario como um objeto

            String resposta = mensagem.getText(); //extrai o texto do usuario

            SendMessage botmessage = SendMessage   //objeto de resposta
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
}