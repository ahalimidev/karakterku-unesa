package com.izanacode.karakter.unesa.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.izanacode.karakter.unesa.databinding.ActivityMenuBinding
import com.izanacode.karakter.unesa.model.Jawaban
import com.izanacode.karakter.unesa.model.*
import com.izanacode.karakter.unesa.server.DatabaseHandler
import com.izanacode.karakter.unesa.viewmodel.materiViewModel
import com.izanacode.karakter.unesa.viewmodel.soalViewModel
import com.izanacode.karakter.unesa.viewmodel.userViewModel
import kotlinx.coroutines.launch

class Menu : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    lateinit var vm: userViewModel
    lateinit var vm1: materiViewModel
    lateinit var vm2: soalViewModel
    val databaseHandler = DatabaseHandler(this@Menu)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(userViewModel::class.java)
        vm1 = ViewModelProvider(this).get(materiViewModel::class.java)
        vm2 = ViewModelProvider(this).get(soalViewModel::class.java)
        binding.nama.text = vm.nama(this@Menu)

        binding.llProfil.setOnClickListener {
            startActivity(Intent(this@Menu, profil::class.java))
        }
        binding.llMateri.setOnClickListener {
            startActivity(Intent(this@Menu, Materi::class.java))
        }
        binding.llIsuTerkini.setOnClickListener {
            startActivity(Intent(this@Menu, MateriBerita::class.java))
        }

        binding.llVideo.setOnClickListener {
            startActivity(Intent(this@Menu, MateriVideo::class.java))
        }

        binding.llTimPengambang.setOnClickListener {
            startActivity(Intent(this@Menu, TimPengambang::class.java))
        }
        binding.llTestKrakterku.setOnClickListener {
            databaseHandler.detele()
            soal()

        }
        binding.llHistori.setOnClickListener {
            startActivity(Intent(this@Menu, History::class.java))

        }
        binding.llLogout.setOnClickListener { vm.Logout(this@Menu, this@Menu) }

        tampil()
    }

    private fun tampil() {
        lifecycleScope.launch {
            try {
                val authResponse = vm1.dashboard_tampil()
                if (authResponse.body()!!.success == 1) {
                    val result = authResponse.body()!!.data

                    binding.tvDesc.text = result.fv_desc
                }
            } catch (throwable: Throwable) {
                Log.e("ERROR", throwable.toString())
            }
        }
    }

    private fun soal(){
        lifecycleScope.launch {
            try {
                val authResponse = vm2.datasoal()
                if (authResponse.body()!!.success == 1) {
                    val result = authResponse.body()!!.data
                    for (x in 0 until result!!.size) {
                        val result1 = result[x].scoretype_tb
                        databaseHandler.AddMateri(
                           Materi(
                                result[x].fn_tocid.toString(),
                                result[x].fv_codetoc.toString(),
                                result[x].fv_nametoc.toString(),
                                result[x].fv_desctoc.toString(),
                            ))
                        for (y in 0 until result1!!.size) {
                            val result2 = result1[y].examanswers_tb
                            databaseHandler.AddMateriDetail(
                               MateriDetail(
                                    result1[y].fn_scoretypeid.toString(),
                                    result[x].fn_tocid.toString(),
                                    result1[y].fv_codescoretype.toString(),
                                    result1[y].fv_namescoretype.toString(),
                                    result1[y].fv_descscoretype.toString(),
                                ))
                            for (q in 0 until result2!!.size) {
                                databaseHandler.AddSoal(
                                    Soal(
                                        result2[q].fn_answersid.toString(),
                                        result1[y].fn_scoretypeid.toString(),
                                        result2[q].fv_titleanswers.toString(),
                                        result2[q].fv_descanswers.toString(),
                                        result2[q].fn_value.toString(),
                                    )
                                )
                            }
                        }
                    }
                    startActivity(Intent(this@Menu, TestKarakter::class.java))
                } else {
                }
            } catch (throwable: Throwable) {
                Log.e("ERROR", throwable.toString())
            }
        }
    }
}