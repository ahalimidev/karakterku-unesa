package com.izanacode.karakter.unesa.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.viewmodel.materiViewModel
import kotlinx.coroutines.launch

import android.webkit.*
import carbon.widget.Button
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import com.izanacode.karakter.unesa.utils.DownloadTask








class pdf : AppCompatActivity() {

    lateinit var vm : materiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        vm = ViewModelProvider(this).get(materiViewModel::class.java)
        tampil_materi()

    }
    private fun tampil_materi (){
        lifecycleScope.launch {
            try {
                val authResponse = vm.pdf(intent.getStringExtra("fn_examresults").toString())
                if (authResponse.body()!!.success == 1) {
                    val awaw = authResponse.body()!!.data
                    val s = "https://karakterku.com/asset/pdf/$awaw"
                    DownloadTask(this@pdf, s)
                }else{
                    finish()
                    Toast.makeText(this@pdf,authResponse.body()!!.message,Toast.LENGTH_LONG).show()

                }
            } catch (throwable: Throwable) {
                Log.e("ERROR",throwable.toString())
            }
        }
    }

}