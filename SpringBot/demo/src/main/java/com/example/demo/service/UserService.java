package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public void registrar(Long chatId, String senha){
        if (!usuarioExiste(chatId)) {
            User novoUsuario = new User();
            novoUsuario.setChatId(chatId);
            novoUsuario.setPassword(senha);
            userRepository.save(novoUsuario);
        }
    }

    public boolean usuarioExiste(Long chatId){
        return userRepository.existsByChatId(chatId);
    }
}
