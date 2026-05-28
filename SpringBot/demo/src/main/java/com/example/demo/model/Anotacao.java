package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Anotacao {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private long chatId;
    private String texto;
    private LocalDateTime horarioNotificacao;

    public Anotacao(){}
    
    public Anotacao(long chatId, String texto){
        this.chatId = chatId;
        this.texto = texto;
    }
}
