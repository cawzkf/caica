# Caíça - Aplicativo de Identificação de Plantas Medicinais Indígenas

**Caíça** é um aplicativo desenvolvido em **Kotlin** no **Android Studio** para identificar e catalogar plantas medicinais indígenas da Amazônia. Utilizando recursos da **AWS**, o app permite ao usuário tirar ou selecionar fotos de plantas e obter informações detalhadas com base em uma análise por labels de imagem.

---

## Funcionalidades

* **Identificação de plantas** a partir de imagens (câmera ou galeria)
* **Catálogo completo** com:
  * Nome popular e nome científico
  * Propriedades medicinais e usos tradicionais
  * Indicações terapêuticas e contraindicações
  * Métodos de preparo e forma de uso
  * Origem do nome e habitat natural
* **Armazenamento seguro** das imagens no **Amazon S3**
* **Análise de imagem** com **AWS Rekognition**
* **Comparação** com múltiplas imagens cadastradas por planta
* **Consulta** a banco de dados **Amazon RDS** (SQL Server)

---

## Tecnologias Utilizadas

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS_S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white)
![AWS Lambda](https://img.shields.io/badge/AWS_Lambda-FF9900?style=for-the-badge&logo=aws-lambda&logoColor=white)
![AWS Rekognition](https://img.shields.io/badge/AWS_Rekognition-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Amazon RDS](https://img.shields.io/badge/Amazon_RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white)
![SQL Server](https://img.shields.io/badge/SQL_Server-CC2927?style=for-the-badge&logo=microsoft-sql-server&logoColor=white)

### Stack Detalhada

* **Kotlin** — Linguagem principal do app
* **Android Studio** — Ambiente de desenvolvimento
* **AWS S3** — Armazenamento das imagens
* **AWS Rekognition** — Reconhecimento de imagem e análise de similaridade
* **AWS Lambda (públicas)** — Funções para processar a imagem e retornar os dados
* **Amazon RDS (SQL Server)** — Banco de dados relacional com as informações das plantas
* **Presigned URLs** — Upload direto das imagens do app para o S3

---

## Como Executar o Projeto

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

---

## Processo de Identificação

1. O usuário tira uma foto ou escolhe da galeria.
2. A imagem é enviada diretamente ao bucket **Amazon S3** via uma **URL temporária (presigned URL)**.
3. O nome da imagem é enviado a uma **função AWS Lambda pública**, que usa o **Rekognition** para comparar com imagens já cadastradas por planta (ex: `img/Murici/`, `img/Jambu/`, etc).
4. Se uma planta for reconhecida, a Lambda retorna o nome correspondente.
5. O app usa esse nome para consultar o **banco de dados RDS** e exibir as informações completas.

---

## Organização no S3

```
bucket-caica/
├── uploads/                    # Imagens enviadas pelos usuários
└── img/
    ├── NOME_DA_PLANTA_1/      # Imagens base organizadas por planta
    ├── NOME_DA_PLANTA_2/
    └── ...
```

* `uploads/` — Imagens enviadas pelos usuários
* `img/NOME_DA_PLANTA/` — Imagens base organizadas por planta

---

## Repositório

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/cawzkf/caica)

**Repositório:** [github.com/cawzkf/caica](https://github.com/cawzkf/caica)

---

<div align="center">

**Desenvolvido por cawzkf com foco educacional e científico**

![Kotlin](https://img.shields.io/badge/Made_with-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![AWS](https://img.shields.io/badge/Powered_by-AWS-232F3E?style=flat-square&logo=amazon-aws&logoColor=white)

</div>
