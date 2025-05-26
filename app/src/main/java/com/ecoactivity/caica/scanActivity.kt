package com.ecoactivity.caica

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.*
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class scanActivity : AppCompatActivity() {

    // Executor usado pela câmera
    private lateinit var cameraExecutor: ExecutorService

    // Componente responsável pela captura da imagem
    private var imageCapture: ImageCapture? = null

    // Referências para os botões e preview da câmera
    private lateinit var previewView: PreviewView
    private lateinit var captureButton: ImageButton
    private lateinit var galeriaButton: ImageButton

    // Controle de debounce para evitar múltiplos cliques rápidos
    private var ultimoClique = 0L

    // URLs das Lambdas AWS
    private val lambdaPresignedUrl = "https://jgt3nyj3c2qngs6ujcohif4q2m0mgcqu.lambda-url.us-east-1.on.aws/"
    private val lambdaRekognitionUrl = "https://hpcrueuzcvf2h7o3veg3muurwu0vplbn.lambda-url.us-east-1.on.aws/"

    // Controle de estado e carregamento
    private var isCapturing = false
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Inicializa componentes de UI
        previewView = findViewById(R.id.camera_preview)
        captureButton = findViewById(R.id.botao_capturar)
        galeriaButton = findViewById(R.id.botao_galeria)

        cameraExecutor = Executors.newSingleThreadExecutor()

        // Clique no botão de captura da câmera
        // Clique no botão de captura da câmera (com debounce)
        captureButton.setOnClickListener {
            if (isCliqueValido()) {
                if (allPermissionsGranted()) takePicture()
                else requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }

        // Clique no botão de selecionar da galeria (com debounce)
        galeriaButton.setOnClickListener {
            if (isCliqueValido()) {
                abrirGaleria()
            }
        }


        // Inicia câmera se tiver permissão
        if (allPermissionsGranted()) startCamera()
        else requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    // Abre a galeria de imagens
    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        startActivityForResult(intent, 1001)
    }

    // Retorno da imagem selecionada na galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data ?: return
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val file = copiarImagemParaArquivo(imageUri, fileName)
            if (file != null) {
                Log.d("GALERIA", "Imagem da galeria salva como: $fileName (${file.length()} bytes)")
                processarImagem(file, fileName)
            }
        }
    }

    // Copia imagem selecionada da galeria para um arquivo local temporário
    private fun copiarImagemParaArquivo(uri: Uri, fileName: String): File? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val tempFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
            val outputStream = FileOutputStream(tempFile)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            tempFile
        } catch (e: Exception) {
            Log.e("Galeria", "Erro ao copiar imagem", e)
            null
        }
    }

    // Captura imagem da câmera e salva no armazenamento público
    private fun takePicture() {
        if (isCapturing) return
        isCapturing = true

        val imageCapture = imageCapture ?: return
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val photoDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Caica")

        if (!photoDir.exists()) photoDir.mkdirs()

        val photoFile = File(photoDir, fileName)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    MediaScannerConnection.scanFile(
                        this@scanActivity,
                        arrayOf(photoFile.absolutePath),
                        arrayOf("image/jpeg")
                    ) { _, _ ->
                        Log.d("CAMERA", "Imagem salva na galeria como: $fileName")
                        processarImagem(photoFile, fileName)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraX", "Erro ao salvar imagem: ${exception.message}", exception)
                    Toast.makeText(applicationContext, "Erro: ${exception.message}", Toast.LENGTH_LONG).show()
                    isCapturing = false
                }
            }
        )
    }

    // Processa a imagem capturada ou selecionada
    private fun processarImagem(file: File, fileName: String) {
        lifecycleScope.launch {
            showLoading("Enviando imagem...")

            if (file.length() < 5000) {
                hideLoading()
                isCapturing = false
                Toast.makeText(this@scanActivity, "Imagem inválida (muito pequena)", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val presignedUrl = solicitarPresignedUrl(fileName)
            if (presignedUrl == null) {
                hideLoading()
                isCapturing = false
                Toast.makeText(this@scanActivity, "Erro ao gerar URL temporária", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val sucessoUpload = uploadImagemParaPresignedUrl(file, presignedUrl)
            if (!sucessoUpload) {
                hideLoading()
                isCapturing = false
                Toast.makeText(this@scanActivity, "Erro ao enviar para o S3", Toast.LENGTH_SHORT).show()
                return@launch
            }

            showLoading("Analisando imagem...")
            val planta = identificarPlantaViaGet(fileName)
            hideLoading()
            isCapturing = false

            if (planta != null) {
                val intent = Intent(this@scanActivity, Infos::class.java).apply {
                    putExtra("planta", planta)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@scanActivity, "Nenhuma planta reconhecida", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Launcher para pedir permissões de forma moderna
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) startCamera()
        else Toast.makeText(this, "Permissões negadas!", Toast.LENGTH_SHORT).show()
    }

    // Verifica se todas as permissões foram concedidas
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    // Inicia a câmera com Preview e ImageCapture
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("CameraX", "Erro ao iniciar a câmera", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    // Exibe um ProgressDialog com mensagem personalizada
    private fun showLoading(message: String = "Processando...") {
        runOnUiThread {
            if (progressDialog == null) {
                progressDialog = ProgressDialog(this).apply {
                    setMessage(message)
                    setCancelable(false)
                    show()
                }
            } else {
                progressDialog?.setMessage(message)
                if (!progressDialog!!.isShowing) progressDialog?.show()
            }
        }
    }

    // Oculta o ProgressDialog
    private fun hideLoading() {
        runOnUiThread {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    // Requisição HTTP para gerar presigned URL via Lambda
    private suspend fun solicitarPresignedUrl(fileName: String): String? = withContext(Dispatchers.IO) {
        try {
            val url = "$lambdaPresignedUrl?fileName=$fileName"
            Log.d("DEBUG", "Requisitando presigned URL: $url")
            val request = Request.Builder().url(url).get().build()
            val response = OkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                val json = JSONObject(response.body?.string() ?: "")
                json.getString("uploadURL")
            } else null
        } catch (e: Exception) {
            Log.e("Presigned", "Erro ao obter presigned URL", e)
            null
        }
    }

    // Faz upload do arquivo para a presigned URL recebida
    private suspend fun uploadImagemParaPresignedUrl(file: File, uploadUrl: String): Boolean = withContext(Dispatchers.IO) {
        try {
            Log.d("DEBUG", "Iniciando upload de: ${file.name} (${file.length()} bytes)")
            val requestBody = file.readBytes().toRequestBody("image/jpeg".toMediaType())
            val request = Request.Builder().url(uploadUrl).put(requestBody).addHeader("Content-Type", "image/jpeg").build()
            OkHttpClient().newCall(request).execute().isSuccessful
        } catch (e: Exception) {
            Log.e("Upload", "Erro ao enviar imagem", e)
            false
        }
    }

    // Verifica se o clique é válido (evita múltiplos cliques rápidos em sequência)
    private fun isCliqueValido(): Boolean {
        val agora = System.currentTimeMillis()
        return if (agora - ultimoClique > 600) {
            ultimoClique = agora
            true
        } else {
            false
        }
    }


    // Chama a Lambda que identifica a planta com base na imagem enviada
    private suspend fun identificarPlantaViaGet(fileName: String): Planta? = withContext(Dispatchers.IO) {
        try {
            val url = "$lambdaRekognitionUrl?fileName=$fileName"
            Log.d("DEBUG", "Chamando Lambda Rekognition: $url")
            val request = Request.Builder().url(url).get().build()
            val response = OkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                val bodyStr = response.body?.string() ?: return@withContext null
                Log.d("DEBUG", "Resposta da Lambda: $bodyStr")
                Gson().fromJson(bodyStr, Planta::class.java)
            } else {
                Log.e("Rekognition", "Erro HTTP: ${response.code}")
                null
            }
        } catch (e: Exception) {
            Log.e("Rekognition", "Erro na requisição", e)
            null
        }
    }

    // Finaliza o executor da câmera
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        // Lista de permissões necessárias para uso da câmera e galeria
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                add(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }.toTypedArray()
    }
}
