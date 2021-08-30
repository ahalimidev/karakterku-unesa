package com.izanacode.karakter.unesa.view

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.izanacode.karakter.model.Rekap
import com.izanacode.karakter.unesa.adapter.SQLITEJRekapAdapter
import com.izanacode.karakter.unesa.databinding.ActivityRekapKarakterBinding
import com.izanacode.karakter.unesa.model.sync.respon
import com.izanacode.karakter.unesa.model.sync.soal
import com.izanacode.karakter.unesa.server.DatabaseHandler
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.soalViewModel
import kotlinx.coroutines.launch


class RekapKarakter : AppCompatActivity() {
    lateinit var binding: ActivityRekapKarakterBinding
    lateinit var sharedPreferences: SharedPreferences
    val databaseHandler = DatabaseHandler(this@RekapKarakter)
    var rekapList = ArrayList<Rekap>()
    var mAdapter: SQLITEJRekapAdapter? = null
    lateinit var vm: soalViewModel
    lateinit var levelPatternGson : String
    lateinit var dialog : Dialog
    var status_jawaban = false
    var status_rekap = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRekapKarakterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(soalViewModel::class.java)
        dialog = progressDialog(this@RekapKarakter)

        sharedPreferences = getSharedPreferences("App", Context.MODE_PRIVATE)
        tampil_rekap(sharedPreferences.getString("fn_userid","").toString())
        soaljawabanhasil(sharedPreferences.getString("fn_userid","").toString())
        binding.btUpload.setOnClickListener {
              if (status_jawaban && status_rekap){
                  simpan(levelPatternGson)
                  binding.btUpload.isEnabled = false
              }else{

              }
        }
    }
    private fun tampil_rekap (fn_userid : String){
        var cursor: Cursor
        cursor = databaseHandler.Rekap(fn_userid)
        if(cursor.count > 0) {
            cursor.moveToFirst()
            do{
                val rekap = Rekap()
                rekap.fn_tocid = cursor.getInt(0).toString()
                rekap.hasil = cursor.getString(1)
                rekap.fv_nametoc = cursor.getString(2)
                rekapList.add(rekap)
            }while(cursor.moveToNext())
            val linearLayoutManager = LinearLayoutManager(this@RekapKarakter, LinearLayoutManager.VERTICAL, false)
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
            mAdapter = SQLITEJRekapAdapter(this@RekapKarakter,rekapList)
            binding.tampil.setLayoutManager(linearLayoutManager)
            binding.tampil.setAdapter(mAdapter)
            mAdapter!!.notifyDataSetChanged()
            status_rekap = true
        }else{
            vm.rekapselesai(this@RekapKarakter)
            finish()
        }
        cursor.close()
    }

   private fun soaljawabanhasil(fn_userid : String){
        val soalsql = databaseHandler.tampiljawaban(fn_userid)
        if(soalsql.count > 0){
            soalsql.moveToFirst()
            val soaldbok =ArrayList<soal>()
            for (x in 0 until soalsql.count) {
                val soaldb = soal()
                soaldb.fn_scoretypeid = soalsql.getString(0)
                soaldb.fn_userid = soalsql.getString(1)
                soaldb.fn_answersid = soalsql.getString(2)
                soaldb.fn_value = soalsql.getString(3)
                soalsql.moveToNext()
                soaldbok.add(soaldb)
            }
            levelPatternGson = Gson().toJson(soaldbok)
            status_jawaban = true
            Log.e("DADADADADADADA",levelPatternGson)
        }

       soalsql.close()
    }


    private  fun simpan(PatternGson : String){
        dialog.show()
        lifecycleScope.launch {
            try {
                val authResponse = vm.simpanjawaban(PatternGson)
                if (authResponse.body()!!.success == 1) {
                    vm.rekapselesai(this@RekapKarakter)
                    dialog.dismiss()
                    finish()
                } else {
                    binding.btUpload.isEnabled = true
                    dialog.dismiss()
                    Toast.makeText(
                        this@RekapKarakter,
                        authResponse.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (throwable: Throwable) {
                dialog.dismiss()
                Log.e("ERROR", throwable.toString())
            }
        }
    }
}