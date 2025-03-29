# Aplicativo de Análise e Identificação de Plantas Medicinais Indígenas

Caíça é um aplicativo desenvolvido em Kotlin no Android Studio para a identificação e análise de plantas medicinais indígenas, utilizando inteligência artificial e visão computacional. O app permite aos usuários capturar imagens de plantas e obter informações detalhadas sobre elas.

## Funcionalidades
- Identificação de plantas medicinais por comparação de imagens
- Exibição de informações detalhadas sobre cada planta, incluindo:
  - Nome popular
  - Nome científico
  - Usos terapêuticos
  - Propriedades medicinais
  - Modo de uso
  - Contraindicações
  - Quando deve ser utilizado
- Integração com Amazon RDS para armazenamento e gerenciamento dos dados do catálogo
- Utilização de Amazon S3 para armazenamento seguro de imagens

## Tecnologias Utilizadas
- **Kotlin** - Linguagem principal do aplicativo
- **Android Studio** - Ambiente de desenvolvimento
- **OpenCV** - Biblioteca para comparação de imagens
- **Amazon Web Services (S3, Lambda, RDS)** - Para armazenamento, processamento e gerenciamento dos dados
- **Amazon RDS** - Banco de dados gerenciado para fornecer informações ao catálogo
- **API REST** - Responsável pela comunicação entre o aplicativo e o banco de dados

## Instalação e Configuração

### Pré-requisitos
- **Android Studio** instalado
- **JDK** atualizado
- Conta na **AWS** com **S3, Lambda e RDS** configurados
- Servidor API (Node.js, .NET, ou outra tecnologia) configurado para consultar os dados do banco

### Clonando o Repositório
```sh
git clone https://github.com/cawzkf/caica.git
cd caica
```

### Rodando o Projeto
```sh
./gradlew assembleDebug
```
Ou abra o projeto no **Android Studio** e clique em **Run**.

## Identificação de Plantas
O processo de identificação de plantas ocorre da seguinte forma:

1. O usuário tira ou seleciona uma imagem da planta.
2. A imagem é enviada para o **AWS S3** para armazenamento seguro.
3. Uma função **AWS Lambda**, utilizando **OpenCV**, realiza a comparação da imagem enviada com imagens cadastradas no sistema.
4. Se houver uma correspondência com alto nível de similaridade, o **ID da planta correspondente** é retornado.
5. O aplicativo busca esse ID no **Amazon RDS** e exibe as informações completas ao usuário.

## Backend
- **API REST** - Responsável por consultar os dados no banco e retornar ao aplicativo
- **AWS Lambda** - Executa um script em Python com **OpenCV** para análise de similaridade entre imagens
- **Amazon RDS** - Banco de dados gerenciado para armazenar e recuperar as informações das plantas

## Repositório
Repositório oficial: [Caíça](https://github.com/cawzkf/caica)

Desenvolvido por **cawzkf**.

