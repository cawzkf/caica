package com.ecoactivity.caica

import android.content.Context
import android.util.Log
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import java.io.File

/**
 * Objeto responsável por fazer upload de arquivos para o Amazon S3
 * Utiliza as credenciais configuradas no BuildConfig para acessar o bucket
 */
object S3Uploader {

    // Nome do bucket onde os arquivos serão enviados
    private const val BUCKET_NAME = "plantas-app-imagens"

    // Pasta dentro do bucket onde os arquivos são salvos
    private const val UPLOAD_PATH = "uploads/"

    // Chaves AWS vindas do BuildConfig gerado pelo Gradle
    private val ACCESS_KEY = BuildConfig.AWS_ACCESS_KEY
    private val SECRET_KEY = BuildConfig.AWS_SECRET_KEY

    /**
     * Envia um arquivo para o Amazon S3 de forma assíncrona
     *
     * @param context contexto Android atual
     * @param file arquivo a ser enviado
     * @param onSuccess callback chamado com o nome do arquivo ao concluir
     * @param onError callback chamado com a exceção em caso de erro
     */
    fun uploadFile(
        context: Context,
        file: File,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        Thread {
            try {
                // Cria as credenciais AWS com a access key e secret key
                val credentials = BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)

                // Cria o cliente S3 para a região us-east-1
                val s3Client = AmazonS3Client(
                    credentials,
                    com.amazonaws.regions.Region.getRegion(Regions.US_EAST_1)
                )

                // Define o caminho do arquivo no bucket
                val fileKey = "$UPLOAD_PATH${file.name}"

                // Prepara a requisição de envio com permissão pública de leitura
                val putRequest = PutObjectRequest(BUCKET_NAME, fileKey, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead)

                // Envia o arquivo para o S3
                s3Client.putObject(putRequest)

                // Loga e chama o sucesso
                Log.d("S3Uploader", "Upload concluído: $fileKey")
                onSuccess(file.name)

            } catch (e: Exception) {
                // Em caso de erro, loga e executa o callback de erro
                Log.e("S3Uploader", "Erro ao enviar: ${e.message}", e)
                onError(e)
            }
        }.start()
    }
}
