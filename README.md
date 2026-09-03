# Sistema para Academias — ExerciteAki

Sistema de gestão para academia de bairro, em Java, com interface desktop em modo caractere. Controle de acesso por perfil, prescrição de treinos, acompanhamento de evolução física e relatórios de frequência.

![Java](https://img.shields.io/badge/Java-17-orange)

---

## O domínio

A ExerciteAki é uma academia independente. O sistema precisa dar conta de três perfis com permissões diferentes e de um modelo de dados com bastante relacionamento entre entidades.

**Administrador** cadastra a academia, os horários de funcionamento, os instrutores e os aparelhos.
**Instrutor** define os treinos dos alunos e registra a evolução física. Cadastra alunos.
**Aluno** consulta o próprio treino e a própria evolução. Não altera nada.

Instrutores têm formações distintas — professor de educação física, atleta profissional, fisioterapeuta.

---

## Modelo

```
Academia
  nome, endereço, telefone, website
  horários de abertura e fechamento variáveis por dia da semana

Instrutor                    Aluno
  nome, e-mail, telefone       nome, e-mail, telefone
  formação                     data de nascimento, altura
  senha de acesso                │
     │ prescreve                 ├── Evolução (peso, % massa muscular, data)
     ▼                           │
  Treino ──────────────────────► └── Frequência (data, entrada, saída)
    dia da semana
    itens: aparelho, ordem, carga, repetições
             │
             ▼
         Aparelho
           nome, descrição, função
```

Um aluno pode ter mais de um treino, executados em dias diferentes da semana.

---

## A decisão técnica central: referência, não cópia

O enunciado é explícito: operações sobre listas de objetos devem ser feitas **por referência**. Se um aparelho já atribuído a um treino tiver os dados alterados, a alteração precisa aparecer em todos os treinos de todos os alunos que usam aquele aparelho.

Na prática, isso significa que um aparelho existe em um único lugar na memória e os treinos guardam a referência a ele. Armazenar cópias produziria divergência silenciosa: o cadastro mostraria o dado novo e os treinos continuariam com o antigo, sem erro nenhum aparecendo.

É a diferença entre entender identidade de objeto e apenas manipular valores.

---

## Funcionalidades

**Cadastros** — academia, horários, instrutores, aparelhos e alunos. Cada um com inclusão, alteração, exclusão e consulta por código e por nome.

**Controle de acesso** — a definição de treinos e o registro de evolução exigem autenticação de instrutor. O aluno tem acesso somente de leitura ao próprio treino.

**Prescrição de treino** — o instrutor monta o treino indicando quais aparelhos, em que ordem, com qual carga e quantas repetições.

**Registro de frequência** — o aluno faz login ao entrar e ao sair. O sistema grava data, horário de entrada e horário de saída.

**Relatórios**

| Relatório | Entrada | Saída |
|---|---|---|
| Treino do dia | nome do aluno, dia da semana | aparelhos, ordem, carga, repetições |
| Evolução | nome do aluno | histórico de peso e percentual de massa muscular |
| Frequência | nome do aluno, período | dias de comparecimento, total de visitas e **total de horas** no período |

O relatório de frequência é o mais exigente: além de filtrar por intervalo, calcula a soma das durações de permanência a partir dos pares entrada/saída.

---

## Persistência e exceções

Os dados são gravados em arquivo e recuperados a cada execução, simulando o comportamento de uma aplicação com banco de dados.

O sistema usa exceções criadas pelo desenvolvedor, e não apenas as da biblioteca padrão, nas violações de regra de negócio — colisão de treinos no mesmo dia da semana e número excessivo de repetições, entre outras.

---

## Como executar

```bash
git clone https://github.com/lgtedescoUCS/Sistema-para-academias.git
cd Sistema-para-academias
javac -d bin -cp lib/* $(find src -name "*.java")
java -cp bin:lib/* Main
```

Ou importe como projeto Java no Eclipse e execute a classe principal.

A navegação é feita por menus em modo caractere, com validação das opções digitadas.

---

Trabalho de implementação T1 e T2 — Programação Orientada a Objetos, Universidade de Caxias do Sul (UCS).

---

> ⚠️ **Confirmar antes de publicar:** o nome da classe principal (usei `Main`), o conteúdo de `lib/`, e quais funcionalidades da parte 2 (persistência em arquivo e exceções próprias) estão de fato implementadas. Se a parte 2 não estiver completa, mova esses itens para uma seção "Em andamento" — igual à do Nexus Hub. Apague este aviso depois.
