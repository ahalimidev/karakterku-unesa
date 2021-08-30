package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.adapter.MateriAdapter
import com.izanacode.karakter.unesa.adapter.MateriBeritaAdapter
import com.izanacode.karakter.unesa.adapter.MateriVideoAdapter
import com.izanacode.karakter.unesa.databinding.ActivityMateriBeritaBinding
import com.izanacode.karakter.unesa.databinding.ActivityMateriBinding
import com.izanacode.karakter.unesa.databinding.ActivityMateriVideoBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.materiViewModel
import kotlinx.coroutines.launch

class MateriBerita : AppCompatActivity() {
    lateinit var binding : ActivityMateriBeritaBinding
    lateinit var dialog : Dialog
    lateinit var vm : materiViewModel
    private var rAdapter : MateriBeritaAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriBeritaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(materiViewModel::class.java)
        dialog = progressDialog(this@MateriBerita)
        tampil_materi()
        binding.ulang.setOnRefreshListener {
            tampil_materi()
            binding.ulang.setRefreshing(false)
        }
        binding.ivBack.setOnClickListener { finish() }
    }
    private fun tampil_materi (){
        dialog.show()
        lifecycleScope.launch {
            try {
                val authResponse = vm.materi_tampil()
                if (authResponse.body()!!.success == 1) {
                    dialog.dismiss()
                    val result = authResponse.body()!!.data
                    val linearLayoutManager = LinearLayoutManager(this@MateriBerita, LinearLayoutManager.VERTICAL, false)
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    rAdapter = MateriBeritaAdapter(this@MateriBerita,result)
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