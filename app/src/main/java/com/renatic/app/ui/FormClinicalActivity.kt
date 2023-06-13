package com.renatic.app.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.Patients
import com.renatic.app.databinding.ActivityFormClinicalBinding
import com.renatic.app.manager.Toolbar2Manager
import com.renatic.app.reduceFileImage
import com.renatic.app.resizeBitmap
import com.renatic.app.response.ClinicalRequest
import com.renatic.app.response.RegisterResponse
import com.renatic.app.response.UploadResponse
import com.renatic.app.uriToBitmap
import com.renatic.app.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FormClinicalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormClinicalBinding
    private lateinit var toolbar: Toolbar2Manager
    private var getFile: File? = null
    private var getData: List<String> = ArrayList()
    private var isUploaded = MutableLiveData<Boolean>()

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File
            myFile?.let { file ->
                getFile = file
                val bitmapImage = BitmapFactory.decodeFile(file.path)
                val resizedBitmap = resizeBitmap(bitmapImage, binding.ivAddPhoto.width, binding.ivAddPhoto.height)
                binding.ivAddPhoto.setImageBitmap(resizedBitmap)
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@FormClinicalActivity)
                getFile = myFile
                val bitmap = uriToBitmap(uri, this)
                bitmap?.let { imageBitmap ->
                    val resizedBitmap = resizeBitmap(imageBitmap, binding.ivAddPhoto.width, binding.ivAddPhoto.height)
                    binding.ivAddPhoto.setImageBitmap(resizedBitmap)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                startCameraX()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormClinicalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        isUploaded.observe(this) {
            if (it) {
                Toast.makeText(this@FormClinicalActivity, "Data berhasil diunggah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnTakePic.setOnClickListener {
            if (!allPermissionsGranted()) {
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                startCameraX()
            }
        }

        binding.btnUploPic.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val choser = Intent.createChooser(intent, "Pilih gambar dari galeri")
            launcherIntentGallery.launch(choser)
        }

        binding.btnSubmit.setOnClickListener {
            getInputData()
            if (getFile != null && getData.all { it.isNotBlank() }) {
                uploadDataClinical()
                uploadDataImage()
            } else {
                Toast.makeText(this@FormClinicalActivity, "Silahkan lengkapi data-data terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun getInputData() {
        val pregnancies = binding.etPregnancies.text.toString()
        val glucose = binding.etGlucose.text.toString()
        val bpressure = binding.etBpressure.text.toString()
        val sthickness = binding.etSthickness.text.toString()
        val insulin = binding.etInsulin.text.toString()
        val bmi = binding.etBmi.text.toString()
        val diabetes = binding.etDiabetes.text.toString()
        getData = listOf(pregnancies, glucose, bpressure, sthickness, insulin, bmi, diabetes)
    }

    private fun uploadDataClinical() {
        if (getData.all { it.isNotBlank() }) {
            val data = ClinicalRequest(getData[0], getData[1], getData[2], getData[3], getData[4], getData[5], getData[6])
            val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "")
            val id = getParceableData()?.id ?: 0
            if (id != 0) {
                isUploaded.value = false
                val client = ApiConfig.getApiService(token.toString()).addClinical(id, data)
                client.enqueue(object: Callback<RegisterResponse>{
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if ((responseBody != null) && !responseBody.error.toBooleanStrict()) {
                                isUploaded.value = true
                                Log.d(TAG, "onResponse : ${responseBody.message}")
                            }
                        } else {
                            Log.d(TAG, "onResponse : ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure : ${t.message}")
                    }
                })
            }
        } else {
            Toast.makeText(this@FormClinicalActivity, "Mohon isi seluruh form input terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadDataImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )
            val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "")
            showLoading(true)
            isUploaded.value = false
            val client = ApiConfig.getApiService(token.toString()).addImage(imageMultipart)
            client.enqueue(object: Callback<UploadResponse>{
                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>,
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if ((responseBody != null) && !responseBody.error.toBooleanStrict()) {
                            Toast.makeText(this@FormClinicalActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@FormClinicalActivity, DetailActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@FormClinicalActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(this@FormClinicalActivity, "Ada kegagalan dalam upload", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            ObjectAnimator.ofFloat(binding.layoutForm, View.ALPHA, 0.5f).start()
            binding.loadForm.visibility = View.VISIBLE
        } else {
            ObjectAnimator.ofFloat(binding.layoutForm, View.ALPHA, 1f).start()
            binding.loadForm.visibility = View.GONE
        }
    }

    @Suppress("DEPRECATION")
    private fun getParceableData(): Patients? {
        return if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DetailActivity.EXTRA_DETAIL, Patients::class.java)
        } else {
            intent.getParcelableExtra(DetailActivity.EXTRA_DETAIL)
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val TAG = "FormClinicalActivity"
    }
}