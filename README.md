
# The Great Programming Journey

Projeto desenvolvido no âmbito da unidade curricular de **Programação / Engenharia Informática** na **Universidade Lusófona**.

---

## 📐 Diagrama UML

O diagrama UML completo do sistema:

![UML Diagram](UML.png)

---

## 🎥 Vídeo de Demonstração

Demonstração do jogo a correr, incluindo:
- Jogo completo
- novo abismo **Undocumented Code** e a ferramenta **ChatGPT**

👉 **Vídeo demo:**  
https://youtu.be/m6JotbGwSIY
---

## 🕳️ Novo Abismo: Undocumented Code

### Conceito
Representa um projeto sem documentação, causando confusão ao programador.

### Comportamento
Quando um jogador cai neste abismo:

1. **Se tiver a ferramenta que o anula diretamente**
   - O abismo é anulado
   - A ferramenta é consumida
   - Nenhum efeito negativo é aplicado

2. **Se NÃO tiver a ferramenta de anulação**
   - O jogador fica no estado **CONFUSED**
   - Fica impedido de jogar durante **N turnos**
   - Onde:
     ```
     N = max(1, último valor do dado / 2)
     ```

3. **Nos turnos seguintes**
   - Quando o contador chega a zero, o jogador volta ao estado normal

### Mensagem apresentada
```
Projeto não documentado! O programador está confuso e precisa de alguns turnos....
```

---

## 🛠️ Nova Ferramenta: ChatGPT

### Conceito
Ferramenta especial que pode ajudar a lidar com problemas inesperados.

### Comportamento geral
- Ao cair numa casa com a ferramenta, o jogador **adquire o ChatGPT**
- A ferramenta é guardada no inventário do jogador

### Interação com Abismos

#### 1️⃣ Abismo Undocumented Code
- O **ChatGPT pode anulá-lo**
- Se for usado:
  - É **sempre consumido**
  - O jogador **não fica confuso**

#### 2️⃣ Outros abismos que tenham counter
- O ChatGPT tem uma **probabilidade de 50%** de anular qualquer abismo que tenha counter
- A decisão é **aleatória**
- **Mesmo que não anule**, o ChatGPT é sempre consumido

### Resumo do ChatGPT
- Pode anular vários tipos de abismos
- Introduz incerteza no jogo
- É sempre consumido quando usado
- Aumenta o fator estratégico

---

## Personalização do tabuleiro

Personalização definida através do método `customizeBoard()` do `GameManager`:

![Personalização do tabuleiro](personalizacao.png)

---

**Ricardo Santos**  
Engenharia Informática  
Universidade Lusófona  
Ano letivo 2025/2026
