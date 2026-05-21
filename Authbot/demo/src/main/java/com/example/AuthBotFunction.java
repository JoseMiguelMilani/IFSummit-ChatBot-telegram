package com.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class AuthBotFunction implements LongPollingSingleThreadUpdateConsumer{ 

    //id, esta logado
    private final Map<Long, Boolean> contasAtivas = new HashMap<>();
    //id, senha
    private final Map<Long, String> contas = new HashMap<>();

    private final TelegramClient bot = new OkHttpTelegramClient(" --seuToken-- "); 

    public void consume(Update update) { 

        if (update.hasMessage() && update.getMessage().hasText()) {  

            Message mensagem = update.getMessage(); 
            String resposta = processarMensagem(mensagem, contas, contasAtivas);

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

    public String processarMensagem(Message mensagem, Map<Long, String> contas, Map<Long, Boolean> contasAtivas){
        Long chatId = mensagem.getChatId();
        String[]texto = mensagem.getText().split(" ",2);
        String resposta = "";

        switch (texto[0]) {
            case "/cadastrar":
                resposta = cadastrar(texto, contas, chatId);
                break;

            case "/logar":
                resposta = logar(texto, contas, contasAtivas, chatId);
                break;

            case "/horario":
                resposta = horario(resposta, chatId, contasAtivas);
                break;

            default:
                resposta = "por favor digite um dos comandos : /cadastrar, /logar, /horario";
                break;
        }

        
        return resposta;
    }
    
    public static String cadastrar(String[] texto, Map<Long, String> contas, Long chatID ){
        //cadastrar "senha"

        if (texto.length < 2) {
            return "esta faltando a senha, digite /cadastrar <sua senha>";
        }

        if (contas.containsKey(chatID)) {
            return "vocẽ ja tem uma conta";
        }

        contas.put(chatID, texto[1]);
        return "conta criada, faça o login com /logar";
    }

    public static String logar(String[] texto, Map<Long, String> contas, Map<Long, Boolean> contasAtivas, Long chatId){

        if (texto.length < 2) {
            return "esta faltando a senha, digite /logar <sua senha>";
        }

        if (!(contas.containsKey(chatId))) {
            return "você não cadastrou, use o comando /cadastrar <sua senha>";
        }

        String senhaDigitada = texto[1];
        String senhaCorreta = contas.get(chatId);

        if (senhaDigitada.equals(senhaCorreta)) {
           contasAtivas.put(chatId, true);
           return "conta logada";
        }

        return "senha incorreta";

    }

    public static String horario(String texto, Long chatId, Map<Long, Boolean> contasAtivas) {
    LocalTime horaAtual = LocalTime.now();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        if (estaLogado(chatId, contasAtivas)) {
            return "hora atual = " + horaAtual.format(formato);
        }

        return "você não tem acesso, faça login com /logar";
    }

    public static boolean estaLogado(Long chatId, Map<Long, Boolean> contasAtivas){
        return contasAtivas.getOrDefault(chatId, false);
    }
}