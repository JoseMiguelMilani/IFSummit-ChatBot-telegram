package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import com.example.demo.model.Anotacao;
import com.example.demo.model.ChatMessage;
import com.example.demo.service.AnotacaoService;
import com.example.demo.service.UserService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringBotFunction implements LongPollingSingleThreadUpdateConsumer { 

    // Injeção via construtor gerada automaticamente pelo Lombok (@RequiredArgsConstructor)
    private final UserService userService;
    private final AnotacaoService anotacaoService;
    
    private TelegramClient bot;

    @Value("${botToken}")
    private String botToken;

    // Gerenciamento de estados do fluxo do bot
    private Map<Long, String> estado = new HashMap<>();
    private Map<Long, Long> anotacaoEsperandoData = new HashMap<>();
    
    @PostConstruct
    public void init() {
        this.bot = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) { 
        if (update.hasMessage() && update.getMessage().hasText()) {  
            Message textoUsuario = update.getMessage(); 
            ChatMessage mensagemUsuario = new ChatMessage(textoUsuario.getText(), textoUsuario.getChatId());

            String resposta = processarResposta(mensagemUsuario);
            enviarMensagem(mensagemUsuario, resposta);
        }
    }

    public String processarResposta(ChatMessage mensagem) {
        String[] argumentos = mensagem.getTexto().split(" ");
        String estadoAtual = estado.get(mensagem.getChatId());

        if ("aguardando_anotacao".equals(estadoAtual)) {
            return criarAnotacao(mensagem);
        }

        if ("aguardando_data".equals(estadoAtual)) {
            return definirHorario(mensagem);
        }

        // Processamento dos comandos principais
        switch (argumentos[0]) {
            case "/start":
                return "Olá! Sou o seu Bloco de Notas pessoal.\n\n" + 
                       "Para prosseguir, primeiro crie sua conta com:\n" +
                       "`/registrar <sua_senha>`\n\n" +
                       "Após isso, use os comandos:\n" + 
                       "`/anotar` - Criar uma nova anotação\n" + 
                       "`/ver` - Ver suas anotações existentes";
        
            case "/registrar":
                return processarRegistrar(argumentos, mensagem);
                
            case "/anotar":
                if (!estaLogado(mensagem)) {
                    return "Você não está logado. Por favor, registre sua conta primeiro.";
                }
                estado.put(mensagem.getChatId(), "aguardando_anotacao");
                return "Escreva sua anotação:";
                
            case "/ver":
                if (!estaLogado(mensagem)) {
                    return "Você não está logado. Por favor, registre sua conta primeiro.";
                }
                List<Anotacao> listaAnotacoes = anotacaoService.listar(mensagem.getChatId());

                if (listaAnotacoes.isEmpty()) {
                    return "Sua lista de anotações está vazia.";
                }
                return criarTabelaAnotacao(listaAnotacoes);

            default:
                return "Não consegui processar seu comando. Use `/start` para ver as opções disponíveis.";
        }
    }

    public String criarTabelaAnotacao(List<Anotacao> listaAnotacoes) {
        StringBuilder stringBuilder = new StringBuilder("*Suas Anotações:* \n\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        
        for (int i = 0; i < listaAnotacoes.size(); i++) {
            Anotacao anotacao = listaAnotacoes.get(i);
            stringBuilder.append(i + 1).append(". ").append(anotacao.getTexto());
            
            if (anotacao.getHorarioNotificacao() != null) {
                stringBuilder.append(" 🔔 (")
                             .append(anotacao.getHorarioNotificacao().format(formatter))
                             .append(")");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public String processarRegistrar(String[] argumentos, ChatMessage mensagem) {
        if (argumentos.length < 2 || argumentos[1] == null || argumentos[1].trim().isEmpty()) {
            return "Você esqueceu de definir a senha. Use: `/registrar <sua_senha>`";
        }

        userService.registrar(mensagem.getChatId(), argumentos[1]);

        if (userService.usuarioExiste(mensagem.getChatId())) {
            return "Você está registrado! Agora pode começar a utilizar os outros comandos.";
        } else {
            return "Ocorreu um erro ao tentar criar sua conta. Tente novamente.";
        }
    }

    public boolean estaLogado(ChatMessage mensagem) {
        return userService.usuarioExiste(mensagem.getChatId());
    }

    public String criarAnotacao(ChatMessage mensagem) {
        estado.remove(mensagem.getChatId());
        
        Anotacao novaAnotacao = anotacaoService.criar(mensagem.getChatId(), mensagem.getTexto());
        anotacaoEsperandoData.put(mensagem.getChatId(), novaAnotacao.getId());
        estado.put(mensagem.getChatId(), "aguardando_data");
        
        return "Anotação salva! Quer agendar uma notificação?\n\n" +
               "Envie a data no formato: `DD/MM/YYYY HH:mm`\n" +
               "Ou envie `/pular` para salvar sem aviso.";
    }

    public String definirHorario(ChatMessage mensagem) {
        if (mensagem.getTexto().equalsIgnoreCase("/pular")) {
            estado.remove(mensagem.getChatId());
            anotacaoEsperandoData.remove(mensagem.getChatId());
            return "Ok! Anotação criada sem notificação.";
        }
        
        try {
            DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime horario = LocalDateTime.parse(mensagem.getTexto(), datetimeformatter);
            
            anotacaoService.agendarNotificacao(anotacaoEsperandoData.get(mensagem.getChatId()), horario);
            
            estado.remove(mensagem.getChatId());
            anotacaoEsperandoData.remove(mensagem.getChatId());
            return "Notificação agendada com sucesso para " + mensagem.getTexto();
            
        } catch (Exception e) {
            return "Formato inválido. Use o padrão `DD/MM/YYYY HH:mm` ou envie `/pular`";
        }
    }

    public void enviarMensagem(ChatMessage mensagemUsuario, String resposta) {
        SendMessage botMessage = SendMessage.builder()
                .chatId(mensagemUsuario.getChatId())
                .text(resposta)
                .build();
        try {
            bot.execute(botMessage);
        } catch (TelegramApiException e) {
            System.err.println("Erro crítico ao responder o usuário no Telegram: " + e.getMessage());
        }
    }
}