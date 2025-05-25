# 🌿 Caíça - Aplicativo de Identificação de Plantas Medicinais Indígenas

**Caíça** é um aplicativo desenvolvido em **Kotlin** no **Android Studio** para identificar e catalogar plantas medicinais indígenas da Amazônia. Utilizando recursos da **AWS**, o app permite ao usuário tirar ou selecionar fotos de plantas e obter informações detalhadas com base em uma análise por similaridade de imagem.

## ✨ Funcionalidades

* 📷 Identificação de plantas a partir de imagens (câmera ou galeria)
* 📚 Catálogo com:

  * Nome popular e nome científico
  * Propriedades medicinais e usos tradicionais
  * Indicações terapêuticas e contraindicações
  * Métodos de preparo e forma de uso
  * Origem do nome e habitat natural
* ☁️ Armazenamento seguro das imagens no **Amazon S3**
* 🧠 Análise de imagem com **AWS Rekognition**
* 🔍 Comparação com múltiplas imagens cadastradas por planta
* 🗃️ Consulta a banco de dados **Amazon RDS** (SQL Server)

## 🔧 Tecnologias Utilizadas

* **Kotlin** — Linguagem principal do app
* **Android Studio** — Ambiente de desenvolvimento
* **AWS S3** — Armazenamento das imagens
* **AWS Rekognition** — Reconhecimento de imagem e análise de similaridade
* **AWS Lambda (públicas)** — Funções para processar a imagem e retornar os dados
* **Amazon RDS (SQL Server)** — Banco de dados relacional com as informações das plantas
* **Presigned URLs** — Upload direto das imagens do app para o S3

## 🚀 Como Executar o Projeto

### Pré-requisitos

* Android Studio instalado com SDK do Kotlin
* JDK 11 ou superior
* Conta na AWS com S3, Rekognition, RDS e Lambda configurados
* Endpoints públicos das funções Lambda acessíveis diretamente pelo app

### Clonando o Projeto

```bash
git clone https://github.com/cawzkf/caica.git
cd caica
```

### Executando

```bash
./gradlew assembleDebug
```

Ou abra no Android Studio e clique em **Run**.

## 🧠 Processo de Identificação

1. O usuário tira uma foto ou escolhe da galeria.
2. A imagem é enviada diretamente ao bucket **Amazon S3** via uma **URL temporária (presigned URL)**.
3. O nome da imagem é enviado a uma **função AWS Lambda pública**, que usa o **Rekognition** para comparar com imagens já cadastradas por planta (ex: `img/Murici/`, `img/Jambu/`, etc).
4. Se uma planta for reconhecida, a Lambda retorna o nome correspondente.
5. O app usa esse nome para consultar o **banco de dados RDS** e exibir as informações completas.

## 📁 Organização no S3

* `uploads/` — Imagens enviadas pelos usuários
* `img/NOME_DA_PLANTA/` — Imagens base organizadas por planta

## 📌 Repositório

🔗 [Repositório no GitHub](https://github.com/cawzkf/caica)

---

Desenvolvido por **cawzkf** com foco educacional e científico 💚
