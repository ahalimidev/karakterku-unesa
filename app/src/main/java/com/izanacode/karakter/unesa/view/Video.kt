package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.izanacode.karakter.unesa.adapter.VideoAdapter
import com.izanacode.karakter.unesa.databinding.ActivityVideoBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.materiViewModel
import kotlinx.coroutines.launch

class Video : AppCompatActivity() {
    lateinit var binding: ActivityVideoBinding
    lateinit var dialog : Dialog
    lateinit var vm : materiViewModel
    private var rAdapter : VideoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(materiViewModel::class.java)
        dialog = progressDialog(this@Video)
        tampil_materi()
        binding.ulang.setOnRefreshListener {
            tampil_materi()
            binding.ulang.setRefreshing(false)
        }
        binding.tvNama.text = intent.getStringExtra("fv_nametoc").toString()
        binding.ivBack.setOnClickListener { finish() }
    }
    private fun tampil_materi (){
        dialog.show()
        lifecycleScope.launch {
            try {
                val authResponse = vm.video_tampil(intent.getStringExtra("fn_tocid").toString())
                if (authResponse.body()!!.success == 1) {
                    dialog.dismiss()
                    val result = authResponse.body()!!.data
                    val linearLayoutManager = LinearLayoutManager(this@Video, LinearLayoutManager.VERTICAL, false)
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    rAdapter = VideoAdapter(this@Video,result)
                    binding.tampil.setLayoutManager(linearLayoutManager)
                    binding.tampil.setAdapter(rAdapter)
                    rAdapter!!.notifyDataSetChanged()
                }else{
                    dialog.dismiss()
                }
            } catch (throwable: Throwable) {
                dialog.dismiss()
                Log.e("ERROR",throwable.toString())
            }
        }
    }
}