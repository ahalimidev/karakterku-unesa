package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.izanacode.karakter.unesa.adapter.MateriDetailAdapter
import com.izanacode.karakter.unesa.databinding.ActivityMateriDetailBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.materiViewModel
import kotlinx.coroutines.launch

class MateriDetail : AppCompatActivity() {
    lateinit var binding: ActivityMateriDetailBinding
    lateinit var dialog : Dialog
    lateinit var vm : materiViewModel
    private var rAdapter : MateriDetailAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(materiViewModel::class.java)
        dialog = progressDialog(this@MateriDetail)
        binding.tvTitle.text = intent.getStringExtra("fv_nametoc").toString()
        binding.tvDe.text = Html.fromHtml(Html.fromHtml(intent.getStringExtra("fv_desctoc")).toString())

        tampil_materi()
        binding.ulang.setOnRefreshListener {
            tampil_materi()
            binding.ulang.isRefreshing = false
        }
        binding.ivBack.setOnClickListener { finish() }
        binding.tampil.isNestedScrollingEnabled = false;

    }

    private fun tampil_materi(){
        dialog.show()
        lifecycleScope.launch {
            try {
                val authResponse = vm.materi_tampil_detail(intent.getStringExtra("fn_tocid").toString())
                if (authResponse.body()!!.success == 1) {
                    dialog.dismiss()
                    val result = authResponse.body()!!.data
                    val linearLayoutManager = LinearLayoutManager(this@MateriDetail, LinearLayoutManager.VERTICAL, false)
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    rAdapter = MateriDetailAdapter(this@MateriDetail,result)
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