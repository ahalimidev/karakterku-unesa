package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.izanacode.karakter.unesa.adapter.MateriAdapter
import com.izanacode.karakter.unesa.adapter.TeamAdapter
import com.izanacode.karakter.unesa.databinding.ActivityTimPengambangBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.materiViewModel
import kotlinx.coroutines.launch

class TimPengambang : AppCompatActivity() {
    lateinit var binding: ActivityTimPengambangBinding
    lateinit var vm: materiViewModel
    lateinit var dialog : Dialog
    private var rAdapter : TeamAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimPengambangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(materiViewModel::class.java)
        dialog = progressDialog(this@TimPengambang)

        binding.ivBack.setOnClickListener { finish() }

        tampil()
    }
    private fun tampil(){
        dialog.show()
        lifecycleScope.launch {
            try {
                val authResponse = vm.timpengembang_tampil()
                if (authResponse.body()!!.success == 1) {
                    dialog.dismiss()
                    val result = authResponse.body()!!.data
                    val linearLayoutManager = LinearLayoutManager(this@TimPengambang, LinearLayoutManager.VERTICAL, false)
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    rAdapter = TeamAdapter(this@TimPengambang,result)
                    binding.rvTampil.setLayoutManager(linearLayoutManager)
                    binding.rvTampil.setAdapter(rAdapter)
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