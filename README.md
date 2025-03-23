# Aplicativo de Análise e Identificação de Plantas Medicinais Indígenas

Este aplicativo, desenvolvido em Kotlin no Android Studio, tem como objetivo identificar e analisar plantas medicinais indígenas. Ele conta com um catálogo de plantas e um sistema de identificação por **comparação de imagens**, sem utilizar modelos de inteligência artificial treinados.

## Funcionalidades

- Identificação de plantas medicinais por comparação de imagens
- Exibição de informações detalhadas sobre cada planta (nome popular, nome científico, usos, indicações, entre outros)
- Integração com banco de dados SQL Server para alimentar a seção do catálogo

## Tecnologias Utilizadas

- Kotlin – Linguagem principal do aplicativo
- Android Studio – Ambiente de desenvolvimento
- OpenCV – Biblioteca para comparação de imagens (usada no backend)
- Amazon Web Services (S3, Lambda, RDS) – Para armazenamento, processamento e gerenciamento dos dados
- SQL Server – Banco de dados local utilizado para fornecer os dados exibidos no catálogo

## Instalação e Configuração

### Pré-requisitos

- Android Studio instalado
- JDK atualizado
- Servidor local com SQL Server ativo
- API (em Node.js, .NET, ou outra tecnologia) para servir os dados do banco
- Conta na AWS com S3, Lambda e RDS configurados

### Clonando o repositório

```bash
git clone https://github.com/cawzkf/caica.git
cd caica
```

### Rodando o projeto

```bash
./gradlew assembleDebug
```

Ou abra o projeto no Android Studio e clique em "Run".

## Identificação de Plantas

O processo de identificação ocorre da seguinte forma:

1. O usuário tira ou seleciona uma imagem da planta.
2. A imagem é enviada para o serviço AWS S3.
3. Uma função Lambda, utilizando OpenCV, realiza a comparação da imagem enviada com imagens já cadastradas no sistema.
4. Se houver uma correspondência com alto nível de similaridade, o ID da planta correspondente é retornado.
5. O app usa esse ID para buscar as informações da planta no banco SQL e exibir para o usuário.

## Backend

- API REST responsável por consultar os dados no banco SQL Server e retornar ao app.
- Serviço Lambda com script em Python e OpenCV para análise de similaridade entre imagens.
- Banco de dados local (SQL Server) para o catálogo atual, com possibilidade de migração futura para o Amazon RDS.

## Repositório

Repositório oficial: [caica](https://github.com/cawzkf/caica.git)

Desenvolvido por cawzkf.
