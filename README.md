# IF Summit - Bot Telegram

Seja bem-vindo ao repositório oficial do nosso minicurso! Esse será o seu guia de estudos e repositório de código. Aqui você encontrará toda a base teórica e os passos práticos que executaremos em sala de aula.
* **Instrutor:** Jose Miguel Milani

![Logo](imagensReadme/ChatBot%20Telegram%20com%20java.jpg)

## sumario

- [Explicação do projeto](#explicação-do-projeto)
- [O que é um ChatBot?](#o-que-é-um-chatbot)
- [Por que telegram](#por-que-telegram)
- [Funcionamento do telegram](#funcionamento-do-telegram)
- [Funcionamento bot](#funcionamento-bot)
- [Tecnologias](#tecnologias)
- [API Telegram](#api-telegram)
- [Criando nosso primeiro bot](#criando-nosso-primeiro-bot)

## 🛠️ Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:
* **Java JDK 17** ou superior.
* **Maven 3.8+** (gerenciador de dependências).
* **VS Code** (com a extensão *Extension Pack for Java*) ou IntelliJ IDEA.

## Explicação do projeto

O objetivo principal deste projeto é construir uma aplicação Java voltada para o Telegram, desde a criação e configuração inicial, até criar um bot capaz de interagir com os usuários e responder a comandos e perguntas pré-definidas.

Para isso utilizaremos as seguintes tecnologias:  
`Java`
`maven`
`Telegram API`
`Spring Boot`

## O que é um chatBot?

Antes de tudo precisamos entender o que é um chatBot.

O termo chatbot se refere a "um software projetado para automatizar tarefas dentro de um chat. Através de processos predefinidos, ele processa as mensagens recebidas e devolve respostas estruturadas."

O chatbot é muito utilizado por empresas que buscam automatizar processos repetitivos com um resultado fixo, como atendimentos online ao cliente, envio de notificações, agendamento de horários e muito mais.

### podemos definir um chatbot em 3 passos:

`Entrada` o usuário manda uma mensagem, onde nela está o que ele deseja encontrar.

`Processamento` o bot recebe a mensagem do usuário, processa, e define a resposta.

`saida` o bot envia a resposta para o usuário.

## Por que telegram

Na criação de um chatbot, a primeira coisa que precisamos é de uma plataforma de conversa, seja ela interna (um chat dentro de um aplicativo), seja ela externa (um aplicativo de chat como WhatsApp e Telegram).

No nosso caso estaremos utilizando uma plataforma externa, sendo ela o Telegram.

### Por que?

O Telegram é uma plataforma pronta e famosa, sendo de fácil acesso para qualquer usuário usá-la.

Além disso, o Telegram tem vários benefícios quando o assunto é criação de bot, como por exemplo:

- **Gratuito** - Outras plataformas de mensagens costumam cobrar para a criação de bots, o Telegram não.

- **API pronta** - Além de não cobrar para a criação de bots, o Telegram oferece uma API com ampla documentação e funcionalidades para a criação dos bots.

- **Simplicidade** - A criação de bot no Telegram é consideravelmente simples comparada com outras plataformas, visto que o Telegram oferece inúmeras funcionalidades para a criação de bots.

## Funcionamento do telegram

Quando usamos o Telegram precisamos lembrar que nunca estamos em contato direto com o usuário, visto que uma das premissas primordiais do Telegram é a sua privacidade.

Toda vez que interagimos com um usuário nós estamos, na verdade, interagindo com um chat em que ambos têm acesso, onde a única informação que temos é a do `chatID`.

Por este motivo o bot não poderá iniciar a mensagem, visto que ele não terá o ID do chat do usuário. Para que ele possa mandar mensagem é preciso que o usuário acesse o chat com o bot e use o comando `/start`.

## Funcionamento bot

Por conta do Telegram ter sido projetado para aceitar sistemas de terceiros, ele é amplamente considerado uma das melhores plataformas para a criação de bot.

Todo programa Telegram passa pelo `Servidor de API do Telegram` que serve como um mediador entre o software e o usuário.

![App Screenshot](imagensReadme/Servidor%20Telegram.png)

O servidor Telegram quebra o processo em:
- **Entrada**: o que o usuário manda, sendo enviado para o servidor central do Telegram.
- **Pacote de entrada**: o Telegram recebe a mensagem do usuário e a transforma em um JSON.
- **Requerimento de pacote**: o programa externo do Telegram faz um pedido ao servidor Telegram pedindo pelo pacote de entrada, que é enviado como um objeto.
- **Saida**: o programa de terceiros devolve uma resposta para o servidor Telegram, que então é enviada para o chat onde o usuário iniciou a conversa.

A forma como o programa externo recebe o pacote de entrada são duas:
- Long Polling
- Webhook

Nós usaremos a forma de Long Polling, que é basicamente ficar perguntando ao servidor Telegram se há alguma mensagem nova e, quando houver, pedir o pacote de entrada com esta mensagem.

Vale ressaltar que na criação do bot é necessário um `token` dado pelo Telegram. Ele serve de identificador único do bot e você deve sempre guardá-lo com extremo cuidado, visto que qualquer um com acesso a ele terá acesso ao seu bot.

## Tecnologias

Para o nosso projeto nós estaremos utilizando 4 tecnologias principais para a criação de nosso bot, sendo elas:

* **JAVA** - Escolhido como linguagem básica, o Java se destaca por sua segurança.

* **MAVEN** - Nosso gerenciador de dependências, responsável por cuidar das nossas dependências de API.

* **TELEGRAM API** - APIs responsáveis por fazer a comunicação entre o programa e o servidor Telegram.

* **SPRING BOOT** - Permite a escalabilidade do projeto para acoplamento de outras tecnologias.

## API Telegram

#### API oficial do Telegram

```http
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots-longpolling</artifactId>
</dependency>
```

#### API porte Telegram para java

```http
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots-longpolling</artifactId>
</dependency>
```


## Criando nosso primeiro bot

com tudo isso em mente podemos começar o nosso primeiro bot.

antes de tudo precisamos definir as pastas principais para a criação do programa

```bash
📦 Projeto Maven  
 ┣ 📂 src    
 ┃     ┣📜 botFunction.java  -- arquivo principal, recebe e processa a mensagem   
 ┃     ┗📜 botStarter.java -- registra o token no servidor Telegram  
 ┗📜 pom.xml  -- gerenciador de dependências
```

após a criação da pasta passamos para o proximo passo que é por as dependencias no arquivo pom.xml

```bash
pom.xml

<dependencies>
 
    <dependency>  <!--API de comunicação com o servidor Telegram-->
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots-client</artifactId>
        <version>9.5.0</version>
    </dependency>

    <dependency>  <!--API de porte Telegram para java-->
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots-longpolling</artifactId>
        <version>9.5.0</version>
    </dependency>

</dependencies>
```

Agora que temos nosso programa configurado, o próximo passo é acessar o @BotFather no Telegram e usar a função /newbot. Após o uso, ela irá pedir um nome para seu bot. Lembre-se que para criar o nome de seu bot não é permitido espaço e é necessário terminar com BOT.

Após você nomear seu bot você irá receber um token. Ele será utilizado para registrar o seu bot. Você deve guardá-lo com segurança, já que qualquer um com acesso ao token terá acesso ao seu bot.

Agora partiremos para a criação e registro de nosso bot. Para isso acessaremos o botStarter.java:

```bash
botStarter.java

botStarter.java

Class botStarter{
    public static void Start(){

        String token = "- Seu token aqui -";
        
        try {
            TelegramBotsLongPollingApplication botTelegramIfpr = new TelegramBotsLongPollingApplication();
            botTelegramIfpr.registerBot(token , new MainBot());

            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
O que esse código faz é criar um objeto TelegramBotsLongPollingApplication, responsável por ficar perguntando ao servidor Telegram por novas mensagens e por registrar o bot com o .registerBot(), enviando o token obtido no BotFather.

Agora que já registramos o bot no servidor e criamos uma conexão, só precisamos receber a mensagem, processá-la e enviar uma resposta para o usuário. Faremos isso no botFunction.java:
```bash
botFunction.java

public class botFunction implements LongPollingSingleThreadUpdateConsumer{  //implementa a função de ouvir o servidor Telegram

    public void consume(Update update) {  //essa função será acionada toda vez que o chat Telegram receber uma nova mensagem

        if (update.hasMessage() && update.getMessage().hasText()) {  //verifica se o usuário mandou um texto

            Message mensagem = update.getMessage();  //extrai as informações contidas na mensagem do usuário

            String resposta = ProcessarResposta(mensagem); //aqui onde ficará sua lógica de negócio

            SendMessage botmessage = SendMessage   //objeto de resposta
                .builder()
                .chatId(mensagem.getChatid())
                .text(resposta)
                .build();
            try {
                bot.execute(botmessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }
}
```
`implements LongPollingSingleThreadUpdateConsumer` - permite com que a classe fique "ouvindo o servidor", aguardando por uma nova mensagem. Caso capte uma nova mensagem, chamará a função consume.

`public void consume(Update update)` - toda vez que a classe recebe uma nova mensagem, o consume, através do objeto Update, extrai as informações contidas no chat.

`Message mensagem` = update.getMessage(); - com essa função extraímos a informação da mensagem, seu texto e o ID do chat onde ela foi enviada. Usaremos então esse objeto para obter todas as informações definidas em nossa regra de negócio.

`SendMessage botmessage` = SendMessage - esse será o objeto que nosso programa enviará ao servidor Telegram para ser enviado ao usuário. Nele nós passamos o ID do chat onde a mensagem será mandada e o texto a ser mandado.




## 🔗 Links
[![Slides](https://img.shields.io)](https://canva.link/ifsummitifpr)

[![ifSummit](https://img.shields.io)](https://ifpr.edu.br/cascavel/if-summit-2006-confira-programacao-e-informacoes/)


Spring boot

- [Spring Initializr](https://start.spring.io/)
- [Documentação Spring boot](https://spring.io/projects/spring-boot)
- [Guia inicial](https://spring.io/guides/gs/spring-boot)
- [Tutorial em video](https://youtu.be/YY_hf0FOIcU?si=XNCgR_SxZPVUzO3T)

Programação Telegram

- [Documentação oficial](https://core.telegram.org/api)
- [Guia telegram oficial ](https://core.telegram.org/bots/tutorial)
- [github da api java](https://github.com/rubenlagus/TelegramBots)
- [tutorial em video](https://youtu.be/XjOnp8TVNSQ?si=YX1q8hSTICU7viSf)
