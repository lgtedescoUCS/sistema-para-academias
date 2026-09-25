# Sistema para Academias — ExerciteAki

Sistema de gestão para academia de bairro, em Java. Controle de acesso por perfil, prescrição de treinos, acompanhamento de evolução física, relatórios de frequência e persistência em JSON.

![Java](https://img.shields.io/badge/Java-17-orange)
![Jackson](https://img.shields.io/badge/Jackson-2.17.0-green)

---

## O domínio

A ExerciteAki é uma academia independente. O sistema atende três perfis com permissões distintas:

**Administrador** cadastra a academia, os horários de funcionamento, os instrutores e os aparelhos.
**Instrutor** prescreve os treinos e registra a evolução física dos alunos.
**Aluno** consulta o próprio treino e a própria evolução — acesso somente de leitura.

---

## Modelagem

A hierarquia parte de uma abstração comum:

```
Pessoa (nome, e-mail, telefone)
  ├── Aluno       + data de nascimento, altura
  └── Instrutor   + Formacao

Usuario / Login / LoginService     autenticação e controle de perfil
Academia + Endereco + Horario      dados institucionais; horário varia por dia da semana

Aluno
  ├── Treino ──► Exercicio ──► Aparelho
  │                ordem, carga, repetições
  ├── Evolucao     peso, % de massa muscular, data
  └── Frequencia   data, entrada, saída
```

**Por que `Pessoa`.** Aluno e instrutor compartilham identificação e contato, mas divergem no resto — o aluno tem altura e data de nascimento, o instrutor tem formação e credencial de acesso. Herdar o núcleo comum evita duplicar os atributos e permite tratar ambos polimorficamente onde a distinção não importa.

**Por que `Exercicio` é uma classe.** Um treino não é uma lista de aparelhos: é uma lista de *prescrições*, cada uma com ordem, carga e repetições próprias. Modelar isso como listas paralelas dentro de `Treino` seria frágil. `Exercicio` carrega a referência ao aparelho e os parâmetros da execução.

**Por que `Endereco` e `Horario` são classes próprias.** Endereço é um agrupamento coeso de campos que não pertence à `Academia` individualmente. Horário precisa variar por dia da semana, o que exige uma coleção de objetos, não pares soltos de atributos.

---

## A decisão técnica central: referência, não cópia

O enunciado exige que as operações sobre listas de objetos sejam feitas **por referência**. Alterar os dados de um aparelho já atribuído a treinos deve refletir em todos os treinos de todos os alunos que o utilizam.

Na prática: um `Aparelho` existe em um único lugar na memória, e cada `Exercicio` guarda a referência a ele. Armazenar cópias produziria divergência silenciosa — o cadastro mostraria o dado novo, os treinos continuariam com o antigo, e nenhum erro apareceria.

---

## Persistência

Os dados são serializados em JSON com **Jackson 2.17.0** e recuperados a cada execução, simulando o comportamento de uma aplicação com banco de dados.

O ponto não-óbvio da serialização é o grafo de objetos: `Exercicio` referencia `Aparelho`, e `Aparelho` aparece em vários treinos. Serializar ingenuamente duplicaria o mesmo aparelho em cada ocorrência, e a desserialização recriaria cópias independentes — quebrando exatamente a regra de referência descrita acima.

---

## Funcionalidades

**Cadastros** — academia, horários, usuários, instrutores, aparelhos e alunos. Cada um com inclusão, alteração, exclusão e consulta por código e por nome.

**Controle de acesso** — `LoginService` autentica e determina o perfil. A prescrição de treinos e o registro de evolução são restritos a instrutores.

**Prescrição de treino** — o instrutor monta o treino indicando aparelhos, ordem, carga e repetições. Um aluno pode ter treinos diferentes para dias diferentes da semana.

**Registro de frequência** — o aluno registra entrada e saída. O sistema grava data e ambos os horários.

**Relatórios**

| Relatório | Entrada | Saída |
|---|---|---|
| Treino do dia | aluno, dia da semana | aparelhos, ordem, carga, repetições |
| Evolução | aluno | histórico de peso e percentual de massa muscular |
| Frequência | aluno, período | dias de comparecimento, total de visitas e **total de horas** no período |

O relatório de frequência é o mais exigente: além de filtrar por intervalo, soma as durações de permanência a partir dos pares entrada/saída.

---

## Estrutura

```
src/
├── model/    entidades de domínio e serviço de autenticação
└── gui/      telas de interação e ponto de entrada (Principal.java)
lib/          jackson-core, jackson-databind, jackson-annotations 2.17.0
```

---

## Como executar

```bash
git clone https://github.com/lgtedescoUCS/sistema-para-academias.git
cd sistema-para-academias

javac -d bin -cp "lib/*" $(find src -name "*.java")
java -cp "bin:lib/*" gui.Principal
```

No Windows, troque os dois-pontos do classpath por ponto e vírgula: `bin;lib/*`.

Também pode ser importado como projeto Java no Eclipse, executando `Principal`.

---

Trabalho de implementação T1 e T2 — Programação Orientada a Objetos, Universidade de Caxias do Sul (UCS).

---

> ⚠️ **Confirmar e apagar este aviso:**
> 1. `gui/` é console (modo caractere, como pede o enunciado) ou Swing? O README está escrito de forma neutra, mas vale explicitar — se for console, escreva "interface em modo caractere"; se for Swing, diga Swing, porque é um diferencial.
> 2. Existe pacote de exceções próprias? Não apareceu no print. Se existir, acrescente uma seção. Se não existir, é o item que falta da parte 2 do trabalho.
> 3. Confirme o package declarado em `Principal.java` — usei `gui.Principal` no comando.
