package com.izanacode.karakter.unesa.view

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.izanacode.karakter.unesa.databinding.ActivityDaftarBinding
import com.izanacode.karakter.unesa.utils.FileCompressor
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.userViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class Daftar : AppCompatActivity() {
    companion object {
        private const val REQUEST_TAKE_PHOTO = 1
        private const val REQUEST_OPEN_GALLERY = 2
        private const val PERMISSION_REQUEST_CODE = 100
    }

    var jk = "L"
    lateinit var binding : ActivityDaftarBinding
    lateinit var vm: userViewModel
    lateinit var dialog : Dialog
    private lateinit var fileCompressor: FileCompressor
    private lateinit var fileImage: File
    var fileImage_UPLOAD: File? = null
    private var selectedSelectImage: Int = 0
    private val listSelectImage = arrayOf("Take Photo", "Choose from Gallery")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(userViewModel::class.java)
        dialog = progressDialog(this@Daftar)
        fileCompressor = FileCompressor(this)

        binding.ivBack.setOnClickListener {
            finish()

        }
        binding.rbJk.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.rbL.id){
                jk = "L"
            }else{
                jk = "P"
            }
        }
        binding.ivTambahFoto.setOnClickListener {
            selectImage()
        }
        binding.btDaftar.setOnClickListener {
            if (binding.etNik.text.toString().isNullOrEmpty()){
                binding.etNik.requestFocus()
                binding.etNik.error = "Kosong"
            }else if (binding.etUsername.text.toString().isNullOrEmpty()){
                binding.etUsername.requestFocus()
                binding.etUsername.error = "Kosong"
            }else if (binding.etPassword.text.toString().isNullOrEmpty()) {
                binding.etPassword.requestFocus()
                binding.etPassword.error = "Kosong"
            }else if (binding.etNama.text.toString().isNullOrEmpty()) {
                binding.etNama.requestFocus()
                binding.etNama.error = "Kosong"
            }else if (binding.etEmail.text.toString().isNullOrEmpty()) {
                binding.etEmail.requestFocus()
                binding.etEmail.error = "Kosong"
            }else if (binding.etTelepon.text.toString().isNullOrEmpty()) {
                binding.etTelepon.requestFocus()
                binding.etTelepon.error = "Kosong"
            }else if (binding.etTelepon.text.toString().isNullOrEmpty()) {
                binding.etTelepon.requestFocus()
                binding.etTelepon.error = "Kosong"
            }else if (fileImage_UPLOAD == null) {
                selectImage()
            }else{
                dialog.show()

                lifecycleScope.launch {
                    try {
                        val authResponse = vm.daftar(
                            RequestBody.create("text/plain".toMediaTypeOrNull(),binding.etNik.text.toString()),
                            RequestBody.create("text/plain".toMediaTypeOrNull(),binding.etUsername.text.toString()),
                            RequestBody.create("text/plain".toMediaTypeOrNull(),binding.etPassword.text.toString()),
                            RequestBody.create("text/plain".toMediaTypeOrNull(),binding.etNama.text.toString()),
                            RequestBody.create("text/plain".toMediaTypeOrNull(), jk),
                            RequestBody.create("text/plain".toMediaTypeOrNull(),binding.etTelepon.text.toString()),
                            RequestBody.create("text/plain".toMediaTypeOrNull(),binding.etEmail.text.toString()),
                            MultipartBody.Part.createFormData("fv_picture", fileImage_UPLOAD!!.name,
                                RequestBody.create("image/*".toMediaTypeOrNull(),fileImage_UPLOAD!!))
                        )
                        if (authResponse.body()!!.success == 1) {
                            dialog.dismiss()
                           startActivity(Intent(this@Daftar,Login::class.java))
                            finish()
                        } else {
                            dialog.dismiss()
                            Toast.makeText(
                                this@Daftar,
                                authResponse.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (throwable: Throwable) {
                        dialog.dismiss()
                        Log.e("ERROR",throwable.toString())
                    }
                }
            }
        }
    }
    private fun selectImage() {
        val builder = AlertDialog.Builder(this)
        builder.setItems(listSelectImage) { _, item ->
            when {
                listSelectImage[item] == "Take Photo" -> {
                    selectedSelectImage = 0
                    if (checkPersmission()) {
                        takePhoto()
                    }
                    else {
                        requestPermission()
                    }
                }
                listSelectImage[item] == "Choose from Gallery" -> {
                    selectedSelectImage = 1
                    if (checkPersmission()) {
                        openGallery()
                    }
                    else {
                        requestPermission()
                    }
                }
            }
        }
        builder.show()
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(this,
            READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                if (selectedSelectImage == 0) {
                    takePhoto()
                } else {
                    openGallery()
                }

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        fileImage = createFile()
        val uri = if(Build.VERSION.SDK_INT >= 24){
            FileProvider.getUriForFile(this, "com.izanacode.karakter.unesa.fileprovider",
                fileImage)
        } else {
            Uri.fromFile(fileImage)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_OPEN_GALLERY)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("IMG_${timeStamp}", ".jpg", storageDir)
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        return try {
            fileImage = createFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()

            val fos = FileOutputStream(fileImage)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            fileImage
        } catch (e: Exception) {
            e.printStackTrace()
            fileImage
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                fileImage_UPLOAD = fileCompressor.compressToFile(fileImage)!!
                binding.tvNamaFoto.text = fileImage_UPLOAD!!.name

            }
        } else if (requestCode == REQUEST_OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, data!!.data!!))
                } else {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, data!!.data!!)
                }
                fileImage_UPLOAD = fileCompressor.compressToFile(bitmapToFile(bitmap))!!
                binding.tvNamaFoto.text = fileImage_UPLOAD!!.name
            }
        }
    }
}