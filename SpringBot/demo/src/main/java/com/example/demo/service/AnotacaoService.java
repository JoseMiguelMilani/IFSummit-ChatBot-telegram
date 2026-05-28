package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Anotacao;
import com.example.demo.repository.AnotacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnotacaoService {
    
    private final AnotacaoRepository anotacaoRepository;

    public Anotacao criar(Long chatId, String texto) {
        return anotacaoRepository.save(new Anotacao(chatId, texto));
    }

    public List<Anotacao> listar(Long chatId) {
        return anotacaoRepository.findByChatId(chatId);
    }

    public void agendarNotificacao(Long id, LocalDateTime horarioAgendado) {
        anotacaoRepository.findById(id).ifPresent(anotacao -> {
            anotacao.setHorarioNotificacao(horarioAgendado);
            anotacaoRepository.save(anotacao);
        });
    }
}
