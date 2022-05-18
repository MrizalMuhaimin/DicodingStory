package com.example.dicodingstory.presentation.intent

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.dicodingstory.R
import com.example.dicodingstory.data.UserPreference
import com.example.dicodingstory.databinding.ActivityAddStoryBinding
import com.example.dicodingstory.presentation.viewmodel.AddNewStoryViewModel
import com.example.dicodingstory.presentation.viewmodel.GetAllStoryViewModel
import com.example.dicodingstory.util.createTempFile
import com.example.dicodingstory.util.reduceFileImage
import com.example.dicodingstory.util.rotateBitmap
import com.example.dicodingstory.util.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var getPhoto: File? = null
    private lateinit var currentPhotoPath: String

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            val noPermission:String = getString(R.string.no_permission)
            if(!allPermissionsGranted()){
                Toast.makeText(
                    this,
                    noPermission,
                    Toast.LENGTH_SHORT
                ).show()
                finish()

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            AddNewStoryViewModel::class.java)

        mainViewModel.isLoading.observe(this,{
            showLoading(it)
        })


        binding.addBtnBack.setOnClickListener {
            finish()
        }
        binding.cameraXButton.setOnClickListener {
            startTakePhoto()
        }
        binding.galleryButton.setOnClickListener{
            startGallery()
        }

        binding.uploadButton.setOnClickListener{

            uploadImage()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }



    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.example.dicodingstory",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)


            getPhoto = myFile
            val result =  BitmapFactory.decodeFile(myFile.path)

            binding.previewImageView.setImageBitmap(result)
        }
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddStoryActivity)
            getPhoto = myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun uploadImage() {
        if (getPhoto != null) {
            if(binding.edtDescStory.text?.isNotEmpty()!!)
            {
                val file = reduceFileImage(getPhoto as File)

                val descriptionText = binding.edtDescStory.text.toString().toRequestBody("text/plain".toMediaType())

                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )

                val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                    AddNewStoryViewModel::class.java)

                val userPreferencer = UserPreference(this)
                val userLogin = userPreferencer.getUser()

                mainViewModel.addStory(userLogin.token.toString(), descriptionText,imageMultipart,null,null)

                mainViewModel.responseData.observe(this,{
                    Toast.makeText(this@AddStoryActivity, it.message, Toast.LENGTH_SHORT).show()

                    if(it.error == false){
                        val getAllStoryViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                            .get(GetAllStoryViewModel::class.java)

                        getAllStoryViewModel.getAllStory(userLogin.token.toString())
                        val intentToStory = Intent(this@AddStoryActivity,DicodingStoryActivity::class.java)
                        startActivity(intentToStory)

                    }
                })


            }else{
                Toast.makeText(this@AddStoryActivity, getString(R.string.description_field), Toast.LENGTH_SHORT).show()


            }
        } else {
            val message = getString(R.string.file_photo_empty)
            Toast.makeText(this@AddStoryActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}