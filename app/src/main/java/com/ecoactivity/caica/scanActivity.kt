package com.ecoactivity.caica

import android.Manifest
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class scanActivity : AppCompatActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var previewView: PreviewView
    private lateinit var captureButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        previewView = findViewById(R.id.camera_preview)
        captureButton = findViewById(R.id.botao_capturar)

        cameraExecutor = Executors.newSingleThreadExecutor()

        captureButton.setOnClickListener {
            if (allPermissionsGranted()) {
                takePicture()
            } else {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }


    }



    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            startCamera()
        } else {
            Toast.makeText(this, "Permissões negadas!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun allPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("CameraX", "Erro ao iniciar a câmera", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePicture() {
        val imageCapture = imageCapture ?: run {
            Toast.makeText(this, "Erro: câmera não inicializada!", Toast.LENGTH_SHORT).show()
            return
        }

        val fileName = "IMG_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera")
            }
        }

        val uri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri == null) {
            Toast.makeText(this, "Erro ao criar arquivo na galeria!", Toast.LENGTH_SHORT).show()
            return
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(contentResolver, uri, contentValues).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(applicationContext, "Foto salva na galeria!", Toast.LENGTH_SHORT).show()
                    Log.d("CameraX", "Foto salva em: $uri")

                    refreshGallery(uri)

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(uri, "image/jpeg")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    startActivity(intent)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(applicationContext, "Erro ao capturar foto: ${exception.message}", Toast.LENGTH_SHORT).show()
                    Log.e("CameraX", "Erro ao salvar imagem", exception)
                }
            }
        )
    }

    private fun refreshGallery(uri: Uri) {
        MediaScannerConnection.scanFile(
            this,
            arrayOf(uri.path),
            arrayOf("image/jpeg")
        ) { _, scannedUri ->
            Log.d("CameraX", "Galeria atualizada para: $scannedUri")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                add(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }.toTypedArray()
    }
}
