package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import com.example.demo.model.Anotacao;
import com.example.demo.repository.AnotacaoRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final AnotacaoRepository anotacaoRepository;
    private TelegramClient bot;
    
    @Value("${botToken}")
    private String botToken;

    @PostConstruct
    public void init() {
        this.bot = new OkHttpTelegramClient(botToken);
    }

    @Scheduled(fixedRate=60000)
    public void verificarNotificao(){
        LocalDateTime agora = LocalDateTime.now();
        List<Anotacao> pendentes = anotacaoRepository.findNotificacaoPendentes(agora);

        for(Anotacao anotacaoPendente:pendentes){
            enviarMensagem(anotacaoPendente.getChatId(), anotacaoPendente.getTexto());
            anotacaoPendente.setHorarioNotificacao(null);//remove notificação
            anotacaoRepository.save(anotacaoPendente);
        }
    }

    private void enviarMensagem(long chatId, String texto){
        SendMessage notificao = SendMessage  
            .builder()
            .chatId(chatId)
            .text(texto)
            .build();
        try {
            bot.execute(notificao);
        } catch (TelegramApiException e) {
            System.err.println("Erro ao enviar mensagem de notificação: " + e.getMessage());
        }
    }
}
