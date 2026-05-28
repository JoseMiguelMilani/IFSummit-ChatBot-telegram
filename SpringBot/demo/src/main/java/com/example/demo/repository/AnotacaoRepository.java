package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Anotacao;

public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {

    List<Anotacao> findByChatId(Long chatId);

    @Query("SELECT a FROM Anotacao a WHERE a.horarioNotificacao <= :agora")
    List<Anotacao> findNotificacaoPendentes(@Param("agora") LocalDateTime agora);
}
