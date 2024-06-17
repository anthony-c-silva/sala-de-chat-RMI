# Sala de chat com RMI

# Descrição Geral do Projeto

Este projeto é uma aplicação de chat distribuída usando Java RMI (Remote Method Invocation). Ele permite que múltiplos usuários se conectem a um servidor de chat, criem e entrem em salas de chat, enviem e recebam mensagens em tempo real. A comunicação entre o cliente e o servidor é feita de maneira remota através do RMI, o que permite que os métodos sejam chamados de um programa rodando em uma máquina diferente.

# Componentes do Projeto

O projeto consiste de várias classes que se dividem entre interfaces e implementações para o servidor e o cliente:

## Interfaces RMI:

- **IRoomChat**: Define os métodos que uma sala de chat deve implementar, como enviar mensagens, entrar, sair e fechar a sala.
- **IServerChat**: Define os métodos que o servidor de chat deve implementar, como listar as salas disponíveis e criar novas salas.
- **IUserChat**: Define os métodos que um usuário de chat deve implementar, como receber mensagens.

## Implementações:

- **RoomChat**: Implementa a interface `IRoomChat`. Gerencia os usuários na sala e as mensagens trocadas.
- **ServerChat**: Implementa a interface `IServerChat`. Gerencia a criação e o fechamento de salas de chat, e mantém uma lista das salas disponíveis.
- **UserChat**: Implementa a interface `IUserChat`. Gerencia a interface gráfica do usuário, permitindo que o usuário entre em salas, envie e receba mensagens.

# Como o Projeto Funciona

## Servidor:

- O servidor (`ServerChat`) é iniciado e cria um registro RMI na porta especificada (por exemplo, 2020).
- O servidor mantém uma lista de salas de chat (`roomList`).
- Usuários podem se conectar ao servidor, listar as salas disponíveis e criar novas salas.

## Salas de Chat:

- Uma sala de chat (`RoomChat`) permite que usuários entrem, saiam e enviem mensagens.
- Mensagens enviadas por um usuário são distribuídas para todos os outros usuários na sala.

## Clientes:

- O cliente (`UserChat`) se conecta ao servidor, lista as salas disponíveis, cria novas salas e entra em salas existentes.
- O cliente tem uma interface gráfica que permite ao usuário enviar mensagens e visualizar as mensagens recebidas.

## Introdução:

"Este é um projeto de chat distribuído usando Java RMI. Ele permite que múltiplos usuários se conectem a um servidor de chat, criem salas, e enviem e recebam mensagens em tempo real."

## Componentes Principais:

"O projeto é composto por três interfaces principais (`IRoomChat`, `IServerChat`, `IUserChat`) e suas respectivas implementações (`RoomChat`, `ServerChat`, `UserChat`)."

"O servidor gerencia a criação e o fechamento de salas de chat, enquanto as salas gerenciam a entrada, saída e comunicação entre os usuários."

## Funcionamento do Servidor:

"O servidor (`ServerChat`) cria um registro RMI e escuta em uma porta especificada. Ele mantém uma lista de salas disponíveis e permite que usuários criem novas salas ou entrem em salas existentes."

## Funcionamento das Salas de Chat:

"Cada sala de chat (`RoomChat`) permite que usuários enviem mensagens que são distribuídas para todos os outros usuários na sala. A sala também gerencia a entrada e saída de usuários, e pode ser fechada pelo servidor."

## Funcionamento do Cliente:

"O cliente (`UserChat`) se conecta ao servidor para listar salas, criar novas salas e entrar em salas existentes. A interface gráfica do cliente permite que o usuário envie mensagens e visualize mensagens recebidas em tempo real."

## Conclusão:

"Este projeto demonstra como usar Java RMI para criar uma aplicação distribuída que permite comunicação em tempo real entre múltiplos usuários. Foi uma experiência valiosa para entender os conceitos de RMI e programação distribuída."
