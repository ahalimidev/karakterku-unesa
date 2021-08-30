package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.izanacode.karakter.unesa.adapter.HistoriDetailAdapter
import com.izanacode.karakter.unesa.databinding.ActivityHistoryDetailBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.soalViewModel
import kotlinx.coroutines.launch

class HistoryDetail : AppCompatActivity() {
    lateinit var binding : ActivityHistoryDetailBinding
    lateinit var vm : soalViewModel
    lateinit var dialog : Dialog
    private var rAdapter : HistoriDetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = progressDialog(this@HistoryDetail)
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
                val authResponse = vm.tampiljawaban(intent.getStringExtra("fn_examresults").toString())
                if (authResponse.body()!!.success == 1) {
                    dialog.dismiss()
                    val result = authResponse.body()!!.data
                    val linearLayoutManager = LinearLayoutManager(this@HistoryDetail, LinearLayoutManager.VERTICAL, false)
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    rAdapter = HistoriDetailAdapter(this@HistoryDetail,result)
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