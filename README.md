Aplicativo de Análise e Identificação de Plantas Medicinais Indígenas

Este aplicativo, desenvolvido em Kotlin no Android Studio, utiliza inteligência artificial para identificar e analisar plantas medicinais indígenas. Através de um modelo de visão computacional, o aplicativo fornece informações detalhadas sobre as plantas, como nome, nome científico, usos, indicações, contraindicações e localização.

Funcionalidades

Identificação de plantas medicinais através de imagens

Informações detalhadas sobre cada planta

Histórico de análises realizadas

Suporte a múltiplos idiomas

Integração com banco de dados SQL para ampliar o catálogo

Tecnologias Utilizadas

Kotlin - Linguagem de programação

Android Studio - IDE de desenvolvimento

Keras - Treinamento do modelo no Kaggle

TensorFlow - Backend para aprendizado profundo

OpenCV & Albumentations - Pré-processamento de imagens

ONNX / TensorFlow Lite - Otimização e deploy do modelo

SQL - Banco de dados para armazenamento de informações

Instalação e Configuração

Pré-requisitos

Android Studio instalado

JDK atualizado

Conta no Firebase (se aplicável)

Banco de dados SQL configurado

Clonando o repositório

git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
cd SEU_REPOSITORIO

Configuração do Firebase (se necessário)

Crie um projeto no Firebase.

Baixe o arquivo google-services.json e coloque na pasta app/.

Ative os serviços necessários (Auth, Firestore, Realtime Database, etc.).

Instalação das Bibliotecas Essenciais

Para o funcionamento do modelo de identificação, instale as seguintes bibliotecas:

pip install keras tensorflow opencv-python albumentations onnx tensorflow-lite

Rodando o projeto

./gradlew assembleDebug

Ou, abra o projeto no Android Studio e clique em "Run".

Treinamento e Implementação

Treinamento do modelo no Kaggle utilizando Keras e TensorFlow.

Ajustes finos (fine-tuning) do modelo conforme necessário.

Implementação do modelo para realizar inferências em imagens, detectando e identificando plantas medicinais.
