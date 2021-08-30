package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.izanacode.karakter.unesa.adapter.HistoriAdapter
import com.izanacode.karakter.unesa.databinding.ActivityHistoryBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.soalViewModel
import kotlinx.coroutines.launch

class History : AppCompatActivity() {
    lateinit var binding : ActivityHistoryBinding
    lateinit var vm : soalViewModel
    lateinit var dialog : Dialog
    private var rAdapter : HistoriAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = progressDialog(this@History)
        binding.ivBack.setOnClickListener {
            finish()
        }
        vm = ViewModelProvider(this).get(soalViewModel::class.java)
        tampil_materi()
    }
    private fun tampil_materi (){
        dialog.show()
        lifecycleScope.launch {
            try {
                val authResponse = vm.tampilrekap(this@History)
                if (authResponse.body()!!.success == 1) {
                    dialog.dismiss()
                    val result = authResponse.body()!!.data
                    val linearLayoutManager = LinearLayoutManager(this@History, LinearLayoutManager.VERTICAL, false)
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    rAdapter = HistoriAdapter(this@History,result)
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