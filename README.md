# Encurtador de URL

Este é um projeto simples de um serviço de encurtamento de URLs, similar ao Bitly. Ele permite que você transforme URLs longas em links curtos e fáceis de compartilhar.

## 🚀 Como Funciona

1.  Você envia uma URL longa para o serviço.
2.  O serviço gera um código curto e único para essa URL.
3.  Ele guarda a URL original e o código curto no banco de dados, com um tempo de validade.
4.  Quando alguém acessa o link curto, o serviço redireciona automaticamente para a URL original.

## ✨ Requisitos do Projeto

O encurtador foi desenvolvido seguindo os seguintes requisitos:

* Recebe uma URL longa como parâmetro inicial para encurtamento.
* O encurtamento (o código curto) é composto por um mínimo de 5 e um máximo de 10 caracteres.
* Apenas letras e números são aceitos no código de encurtamento.
* A URL encurtada é salva no banco de dados com um prazo de validade (atualmente configurado para 5 minutos de expiração).
* Ao receber uma chamada para uma URL encurtada (ex: `https://xxx.com/DXBV6`), ele deve fazer o redirecionamento para a URL original salva no banco de dados.
* Caso uma URL não seja encontrada (por não existir ou ter expirado), o serviço retorna o status HTTP `404 Not Found`.

## 🛠️ Tecnologias e Ferramentas Utilizadas

As seguintes tecnologias e ferramentas foram empregadas no desenvolvimento deste projeto:

<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" alt="Java" width="60" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/mongodb/mongodb-original.svg" alt="MongoDB" width="60" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original.svg" alt="Spring Boot" width="60" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/docker/docker-original.svg" alt="Docker" width="60" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/apache/apache-original.svg" alt="Apache" width="60" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/insomnia/insomnia-original.svg" alt="Insomnia" width="60" height="60"/>
</p>

* **Java**: Linguagem de programação principal utilizada.
* **Spring Boot**: Framework que facilita a criação de aplicações Java, especialmente APIs REST.
* **MongoDB**: Banco de dados NoSQL utilizado para armazenar as URLs originais e seus respectivos códigos curtos.
* **Docker**: Usado para empacotar a aplicação e suas dependências, facilitando a execução em diferentes ambientes.
* **Apache**: Servidor web (muitas vezes usado junto com aplicações Java, embora o Spring Boot já venha com um servidor embutido, Apache pode ser usado como proxy reverso, por exemplo).
* **Insomnia**: Ferramenta utilizada para testar as requisições HTTP da API (como enviar URLs para encurtar e testar o redirecionamento).

## 📂 Estrutura do Projeto

* `src/main/java/tech/diogoalmeida/urlshortener/UrlshortenerApplication.java`: O ponto de entrada principal da aplicação Spring Boot.
* `src/main/java/tech/diogoalmeida/urlshortener/controller/UrlController.java`: Contém os "endpoints" (endereços) da API que recebem as requisições para encurtar URLs e redirecionar as URLs curtas.
* `src/main/java/tech/diogoalmeida/urlshortener/entities/UrlEntity.java`: Define a estrutura de como uma URL é guardada no banco de dados MongoDB.

## ⚙️ Como Rodar o Projeto (Instruções Básicas)

Para rodar este projeto, você precisará ter o Java, Docker e MongoDB instalados.

1.  **Clone o Repositório:**
    ```bash
    git clone [LINK_DO_SEU_REPOSITORIO]
    cd url-shortener
    ```
    *(Substitua `[LINK_DO_SEU_REPOSITORIO]` pelo link real do seu repositório Git, ex: `https://github.com/seu-usuario/seu-projeto.git`)*

2.  **Configure o MongoDB:**
    * Garanta que uma instância do MongoDB esteja rodando. Você pode usar o Docker para isso:
        ```bash
        docker run -d -p 27017:27017 --name my-mongo mongo
        ```

3.  **Compile e Execute a Aplicação Spring Boot:**
    * Você pode compilar o projeto usando Maven ou Gradle (se tiver configurado).
    * Para rodar diretamente (certifique-se de estar na pasta raiz do projeto onde está o `pom.xml` ou `build.gradle`):
        ```bash
        ./mvnw spring-boot:run # Se estiver usando Maven Wrapper
        # Ou, se já tiver compilado o JAR:
        java -jar target/url-shortener-*.jar
        ```
    * Certifique-se de que o aplicativo se conecte corretamente à sua instância do MongoDB.

## 🧪 Como Testar a API

Você pode usar o **Insomnia** (ou Postman) para testar os endpoints:

1.  **Encurtar uma URL (POST)**
    * **Método:** `POST`
    * **URL:** `http://localhost:8080/shorten-url`
    * **Body (JSON):**
        ```json
        {
          "url": "[https://www.example.com/sua/longa/url/que/voce/quer/encurtar](https://www.example.com/sua/longa/url/que/voce/quer/encurtar)"
        }
        ```
    * Você receberá uma resposta com a URL encurtada, por exemplo:
        ```json
        {
          "shortenedUrl": "http://localhost:8080/XYZ123"
        }
        ```

2.  **Acessar a URL Encurtada (GET)**
    * **Método:** `GET`
    * **URL:** `http://localhost:8080/XYZ123` (substitua `XYZ123` pelo código que você recebeu na etapa anterior)
    * Se a URL existir e não estiver expirada, você será redirecionado para a URL original. Se não, receberá um status `404 Not Found`.