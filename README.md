# AlertHook

AlertHook é um aplicativo Android moderno e intuitivo desenvolvido em Kotlin com Jetpack Compose, projetado para atuar como um roteador de notificações. Ele permite que os usuários interceptem notificações de aplicativos selecionados e as enviem para um webhook configurável, oferecendo controle total sobre quais informações são compartilhadas e para onde.

## Funcionalidades

-   **Escuta de Notificações:** Intercepta todas as notificações recebidas no dispositivo.
-   **Filtragem por Aplicativo:** Permite ao usuário selecionar quais aplicativos terão suas notificações monitoradas.
-   **Configuração de Webhook:** Configure facilmente uma URL de webhook para onde as notificações processadas serão enviadas.
-   **Payload Personalizável:** Opções para personalizar o formato do payload JSON enviado ao webhook, incluindo título, texto, nome do pacote do aplicativo, etc.
-   **Interface Intuitiva:** Desenvolvido com Jetpack Compose para uma experiência de usuário fluida, moderna e visualmente atraente, seguindo os princípios do Material Design 3.

## Tecnologias Utilizadas

-   **Linguagem:** Kotlin
-   **UI Framework:** Jetpack Compose
-   **Arquitetura:** MVVM (Model-View-ViewModel)
-   **Persistência de Dados:** (A ser implementado, provavelmente Jetpack DataStore ou Room)

## Primeiros Passos

Para configurar e executar o projeto localmente, siga estas etapas:

### Pré-requisitos

-   Android Studio (versão recomendada: Flamingo ou superior)
-   SDK do Android (API Level 24 ou superior)
-   Java Development Kit (JDK) 11 ou superior

### Instalação

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/AlertHook.git
    cd AlertHook
    ```

2.  **Abra o projeto no Android Studio:**
    -   No Android Studio, selecione `File > Open` e navegue até o diretório `AlertHook` que você acabou de clonar.
    -   Aguarde o Android Studio sincronizar o Gradle e baixar todas as dependências.

3.  **Execute o aplicativo:**
    -   Conecte um dispositivo Android ou inicie um emulador.
    -   Clique no botão `Run` (o ícone de triângulo verde) na barra de ferramentas do Android Studio.

## Uso

Após instalar e abrir o aplicativo, você precisará conceder a permissão de acesso às notificações. O aplicativo irá guiá-lo para as configurações do sistema para habilitar esta permissão.

Uma vez concedida a permissão, você poderá:

-   Navegar até a tela de configurações para inserir a URL do seu webhook.
-   Selecionar os aplicativos dos quais você deseja monitorar as notificações.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues para bugs ou sugestões de recursos, ou enviar pull requests com melhorias.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes. (O arquivo LICENSE será criado posteriormente)
