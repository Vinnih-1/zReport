# **zReport**

-----------

#### Comandos & Permissões

| Comandos   | Permissão          | Local   | Função                                                    |
|------------|--------------------|---------|-----------------------------------------------------------|
| report     | Nenhuma            | In-Game | Reporta o jogador indicado no argumento                   |
| reports    | zreport.seereport  | In-Game | Cria menu todos as denúncias do servidor ou de um jogador |
| reportinfo | zreport.seereport  | In-Game | Cria menu com todas as denúncias do jogador indicado      |
|            | zreport.fastreport | In-Game | Permissão para poder usar o /report (Jogador) (Motivo)    |

------------

## Caso encontre algum bug entre em contato comigo pelo nosso [discord](https://discord.gg/aRZEqZ8HyJ)

Agora o zReport baixa as dependências automaticamente durante a inicialização do mesmo.

### Como instalar o plugin

Coloque o zReport na pasta plugins de seu servidor BungeeCord e ligue-o. Após isso configure seu banco de dados e seu bot (opcional) no arquivo config.yml. Agora é só ligar seu servidor BungeeCord e divirta-se.

## Lista de Modificações

------------

- Readicionado suporte ao BungeeCord
- Adicionado comando /reports & /reportinfo
- Adicionado sistema de prioridades para uma futura integração com o [zPunish](https://mercado-minecraft.com.br/produtos/96447-zPunish/detalhes)
- Várias novas mensagens configuráveis adicionadas à config.yml

## Plugin in-game

![zReportATT](https://user-images.githubusercontent.com/59892753/163983224-125a1ff4-63fd-450c-9bc3-1818dbaccac7.gif)

### **Config.yml**

```
###################################################
###################################################
###                                             ###
###          Plugin feito pela zPluginS         ###
###            zReport versão: 1.0.6            ###
###           Integrado com o discord           ###
###     Continua em fase de desenvolvimento     ###
###                                             ###
###################################################
###################################################

# Insira sua licença aqui
license: 'XXXX-XXXX-XXXX-XXXX'

bot:
  enabled: false # Diz se o bot irá ser ligado quando o servidor inicializar
  chat-id: 'ID do chat' #ID do canal que o bot irá usar para notificar as punições
  status: 'zReport | Feito pela zPluginS' # Status que aparecerá no bot quando ele for ligado
  token: 'Token' #Token do bot (https://discord.com/developers/applications)

report:
  server: 'Lobby' # Caso esteja usando o zReport em um spigot, digite o servidor aqui
  timeout: 5 # O tempo-limite que um report leva para expirar em minuto(s)
  motivos: # Lista de motivos que irão aparecer para o usuário quando estiver efetuando uma denúncia.
    - 'Uso de Hack'
    - 'Abuso de Bugs'
    - 'Divulgação de IPs'
    - 'Ofensa a Staff'

mensagem-discord: # As mensagens que aparecerão no discord em uma seção separada
  embed:
    report:
      title: 'Novo Report'
      thumbnail: 'https://minotar.net/avatar/%apelido/100.png'
      footer-image: 'https://i.imgur.com/r4Y1aMLh.jpg'
      footer: 'zPluginS © Todos os direitos reservados.'
      builder:
        1:
          name: 'ID'
          value: '%id'
          inline: true
        2:
          name: 'Apelido'
          value: '%apelido'
          inline: true
        3:
          name: 'Autor'
          value: '%autor'
          inline: true
        4:
          name: 'Prioridade'
          value: '**%prioridade**'
          inline: true
        5:
          name: 'Motivo'
          value: '%motivo'
          inline: true
        6:
          name: 'Prova'
          value: '%prova'
          inline: true
        7:
          name: 'Servidor'
          value: '%server'
          inline: false
        8:
          name: 'Data'
          value: '%data'
          inline: false

mensagem-server:
  mensagens:
    player-offline: '&c&lERRO &fEste jogador não está online!'
    sem-permissao: '&c&lERRO &fVocê não pode usar este comando!'
    sem-provas: '&c&lERRO &fVocê não pode denunciar sem provas!'
    comando-bloqueado: '&c&lERRO &fImpossível executar comandos antes do denúncia!'
    denuncia-nao-encontrada: '&c&lERRO &fNão há nenhuma denúncia pendente sobre este jogador!'
    denunucia-enviada: '&a&lSUCESSO &fVocê enviou sua denúncia para nossa equipe!'
    denuncia-aprovada: '&a&lSUCESSO &fGraças a você, nós punimos mais um jogador!'
    denuncia-recusada: '&a&lSUCESSO &fVocê recusou esta denúncia!'

  denuncia-prova-mensagem: # Mensagem de provas que aparecerá para o jogador que está efetuando a denúncia
    - ''
    - '&aInsira a o link das suas provas da denúncia.'
    - '&7Digite &nnenhuma&7 para não colocar provas.'

  alerta-staff-mensagem: # Mensagem de report que irá aparecer para os staff's presentes no servidor.
    - ''
    - '  &c&lREPORT'
    - '  &c%apelido &ffoi reportado por &c%autor'
    - '  &fMotivo: &c%motivo'
    - '  &fProva: &c%prova'
    - '  &fVezes: &c%vezes'
    - ''
    - '  &fPrioridade: %prioridade'
    - ''
```

