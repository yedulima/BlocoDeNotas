# 📝 Bloco de Notas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Um editor de texto simples desenvolvido em **Java** com interface gráfica **Swing**, inspirado no Notepad do Windows.

## 🔧 Funcionalidades

- Criar, abrir e salvar arquivos .txt
- Detecção de alterações não salvas com prompt de confirmação
- Quebra automática de linha
- Suporte a múltiplas janelas simultâneas
- Atalhos de teclado para as principais ações

## 🖥️ Tecnologias

- Java
- JavaSwing
- Java AWT

## Como usar

### Pré-requisitos

- Java 8 ou superior instalado. Caso não tenha, baixe em [java.com](https://www.java.com/pt-br/download/manual.jsp).

### Executando o projeto

**Via IDE (IntelliJ, Eclipse, NetBeans)**
1. Clone o repositório
```bash
git clone https://github.com/yedulima/BlocoDeNotas.git
```

2. Abra o projeto na sua IDE
3. Execute a classe [Main](src/com/eduardo/BlocoDeNotas/Main.java)

**Via terminal**
1. Clone o repositório
```bash
git clone https://github.com/yedulima/BlocoDeNotas.git
```

2. Compile o projeto
```bash
javac -d out src/com/eduardo/BlocoDeNotas/Main.java src/com/eduardo/BlocoDeNotas/model/DocumentState.java src/com/eduardo/BlocoDeNotas/io/DocumentIO.java src/com/eduardo/BlocoDeNotas/controller/DocumentController.java src/com/eduardo/BlocoDeNotas/ui/TextAreaPanel.java src/com/eduardo/BlocoDeNotas/ui/MenuBarBuilder.java src/com/eduardo/BlocoDeNotas/ui/MainFrame.java
```

2. Compile o projeto
```bash
java -cp out com.eduardo.BlocoDeNotas.Main
```

## Preview

<img width="1920" height="1036" alt="image" src="https://github.com/user-attachments/assets/cd29d805-1765-4c20-b692-9b9e9f152aa8" />

<img width="1920" height="1044" alt="image" src="https://github.com/user-attachments/assets/dc52fc40-7184-4da0-94ae-55e134be968e" />

<img width="1920" height="1044" alt="image" src="https://github.com/user-attachments/assets/02a2603d-d4c6-4aec-ae51-b2c792102480" />
