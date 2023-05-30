package com.renatic.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.renatic.app.databinding.ActivityFormRetinaBinding
import com.renatic.app.viewManager.Toolbar2Manager
import java.io.File

class FormRetinaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormRetinaBinding
    private lateinit var toolbar: Toolbar2Manager
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRetinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        binding.ibAddPhoto.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val choser = Intent.createChooser(intent, "Pilih gambar dari galeri")
            launcherIntentGallery.launch(choser)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@FormRetinaActivity)
                getFile = myFile
                val bitmap = uriToBitmap(uri, this)
                bitmap?.let { imageBitmap ->
                    val resizedBitmap = resizeBitmap(imageBitmap, binding.ibAddPhoto.width, binding.ibAddPhoto.height)
                    binding.ibAddPhoto.setImageBitmap(resizedBitmap)
                }
            }
        }
    }
}