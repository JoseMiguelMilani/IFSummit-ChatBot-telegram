
# IF Summit - Bot Telegram


Seja bem-vindo ao repositório oficial do nosso minicurso! esse sera o seu  guia de estudos e repositório de código. Aqui você encontrará toda a base teórica e os passos práticos que executaremos em sala de aula.
* **Instrutor:** Jose Miguel Milani




![Logo](imagensReadme/ChatBot%20Telegram%20com%20java.jpg)


## sumario

- [Explicação do projeto](#explicação-do-projeto)
- [O que é um ChatBot](#o-que-é-um-chatbot)
- [Por que o Telegram](#por-por-que-telegram)
- [Funcionamento do Telegram](#funcionamento-do-telegram)
- [Funcionamento do Bot](#funcionamento-bot)
- [Tecnologias Utilizadas](#tecnologias)
- [APIs e Dependências](#api-telegram)
- [Criando nosso primeiro bot](#criando-nosso-primeiro-bot)
- [O Desafio Prático: Chatbot Autenticado](#-o-desafio-prático-chatbot-autenticado)

## 🛠️ Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:
* **Java JDK 17** ou superior.
* **Maven 3.8+** (gerenciador de dependências).
* **VS Code** (com a extensão *Extension Pack for Java*) ou IntelliJ IDEA.

## Explicação do projeto

O objetivo principal deste projeto é construir uma aplicação Java voltada para o Telegram, desde a criação e confuguração inicial, até criar um bot capaz de interagir com os usuários e responder a comandos e perguntas pré-definidas 

para isso utilizaremos as seguintes tecnologias:  
`Java`
`maven`
`Telegram API`
`Spring Boot`


## O que é um chatBot?

Antes de tudo precisamos entender o que é um chatBot

O termo chatbot se refere a "um software projetado para automatizar tarefas dentro de um chat. Através de processos predefinidos, ele processa as mensagens recebidas e devolve respostas estruturadas."

o chatbot é muito utilizado por empresas que buscam automatizar processos repetitivos com um resultado fixo, como atendimentos online ao cliente, enviu de notificações,  agendamento de horários e muito mais.

### podemos definir um chatbot em 3 passos:

`Entrada ` o usuario manda uma mensagem, onde nela esta o que ele deseja encontrar

`Processamento`o bot recebe a mensagem do usuario, processam, e define a resposta

`saida`o bot envia a resposta para o usuario
## Por que telegram

Na criação de um chatbot, a primeira coisa que precisamos é de uma plataforma de conversa, seja ela interna (um chat dentro de um aplicativo), seja ela externa (um aplicativo de chat como WhatsAap e Telegram)

no nosso caso estaremos utilizando uma plataforma externa, sendo ela o Telegram

### Por que?

o Telegram é uma plataforma pronta e famosa, sendo de facil acesso para qualquer usuario usa-la

alem disso o Telegram tem varios beneficios quando o assunto é criação de bot, como por exemplo:

- Gratuito - Outras plataformas de mensagens custumam cobrar para a criação de bots, o Telegram não

- API pronta - Além de não cobrar para a criação de bots, o Telegram oferece uma API com ampla documentação  e funcionalidades para a criação dos bots

- Simplicidade - A criação de bot no Telegram é consideravelmente simples comparada com outras plataformas, visto que o Telegram oferece inumeras funcionalidades para a criação de bots
## Funcionamento do telegram

quandos usamos o Telegram precisamos lembrar que nunca estamos em contato direto com o usuario, visto que uma das premissas primordiais do Telegram é sua privacidade

toda vez que interagimos com um usuario nós estamos na verdade interagindo com um chat em que ambos tem acesso, onde a unica informação que temos é a do `chatID`

por este motivo o bot não podera iniciar a mensagem, visto que ele não tera o id do chat do usario, para que ele possa mandar mensagem é preciso que o usuario acesse o chat com o bot e use o comando `/start`


## Funcionamento bot

Por conta do Telegram ter sido projetado para aceitar sistemas de terceiro ele é amplamente considerado uma das melhores plataformas para a criação de bot

Todo programa Telegram passa pelo `Servidor de API do Telegram` que serve como um mediador entre software e usuario

![App Screenshot](imagensReadme/Servidor%20Telegram.png)


O servido Telegram quebra o processo em:
- **Entrada** : o que o usuario manda, sendo enviado para o servidor central do Telegram
- **Pacote de entrada**: o Telegram recebe a mensagem do usuario e a transforma em um JSON
- **Requerimento de pacote**: o programa externo do telegram faz um pedido ao servidor telegram pedindo pelo pacote de entrada, que é enviado como um objeto 
- **Saida**: o programa de terceiro devolve uma resposta para o servidor Telegram, que então é enviada para o chat onde o usuario iniciou a conversa

A forma como o programa externo recebe o pacote de entrada são duas:
- Long Pooling
- Webhook

nós usaremos a forma de Long Pooling, que é basicamente ficar perguntando ao servidor Telegram se há alguma mensagem nova, e quando houver pedir para o pacote de entrada com esta mensagem

Vale se dizer que na criação do bot é necessario um `token dado pelo Telegram, ele serve de identificador unico do bot, você deve sempre guarda-lo com extremo cuidado, visto que qualquer um com acesso a ele tera acesso ao seu bot
## Tecnologias
Para o nosso projeto nós estaremos utilizando 4 Tecnologias principais  para criação de nosso bot, sendo elas:

* **JAVA** - Escolhido como linguagem basica o Java se destaca por sua segurança

* **MAVEN** - Nosso gerenciador de dependencia, responsavel por cuidar das nossas dependencia de API

* **TELEGRAM API** - APIs resposaveis por fazer a comunicação entre programa e servidor Telegram

* **SPRING BOOT** - permite a escalabilidade do projeto para aclopação de outras tecnologias

## API Telegram

#### API oficial do Telegram

```http
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots-client</artifactId>
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
 ┃     ┗📜 botStarter.java -- registrada o token no server Telegram  
 ┗📜 pom.xml  -- gerenciador de dependencia
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

</dependency>
```

agora que temos nosso programa configurado o proximo passo e acessar o  `@BotFather` no telegram e usar a função `/newbot`, após o uso ela ira pedir por um nome para seu bot, lembre-se que para criar o nome de seu bot não é permitido espaço e é necessario terminar com BOT

Após você nomear seu bot você ira receber um `token`, ele sera utilizado para registrar o seu bot. Você deve guarda-lo com segurança, ja que qualquer um com acesso ao token ira ter acesso ao seu bot

Agora partiremos para a criação e registro de nosso bot, para isso acessaremos o botStarter.java

```bash
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
o que esse codigo faz é criar um objeto `TeleTelegramBotsLongPollingApplication`, resposavel por ficar perguntando ao servidor Telegram por novas mensagens e por registrar o bot com o `.registerbot()`, enviando o token obtido no BotFather

agora que ja registramos o bot no servidor, e criamos uma conexão só precisamos receber a mensagem, processa-la e enviar uma resposta para o usuario, faremos isso no `botFunction.java`

```bash
botFunction.java

public class botFunction implements LongPollingSingleThreadUpdateConsumer{  //implementa a função de ouvir o servidor Telegram

    public void consume(Update update) {  //essa função sera acionada toda vez que o chat Telegram receber uma nova mensagem

        if (update.hasMessage() && update.getMessage().hasText()) {  //verifica se o usaurio mandou um texto

            Message mensagem = update.getMessage();  //extrai as informações contidas na mensagem do usuario

            String resposta = ProcessarResposta(mensagem); //aqui onde ficara sua lógica de negocio

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
`implements LongPollingSingleThreadUpdateConsumer`- permite com que a classe fique "ouvindo o servidor", aguardando por uma nova mensagem, caso capte uma nova mensagem chamara a função`consume`

`public void consume(Update update)`- toda vez que a classe recebe uma nova mensagem o consume, atráves do objeto `Update` extrai as informações contidas no chat

`Message mensagem = update.getMessage();` com essa função extraimos a informação da mensagem, seu texto e o id do chat onde ela foi enviada, usaremos então esse objeto para obter todas informações definidas em nossa regra de negocio

`SendMessage botmessage = SendMessage` esse sera o objeto que nosso programa enviara ao servidor telegram para ser enviado ao usuario, nele nós passamos o id do chat onde a mensagem sera mandada e o texto a ser mandado




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
