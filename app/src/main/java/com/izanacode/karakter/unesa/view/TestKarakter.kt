package com.izanacode.karakter.unesa.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.adapter.SQLITEJawabanAdapter
import com.izanacode.karakter.unesa.databinding.ActivityTestKarakterBinding
import com.izanacode.karakter.unesa.model.Jawaban
import com.izanacode.karakter.unesa.model.Soal
import com.izanacode.karakter.unesa.server.DatabaseHandler
import com.izanacode.karakter.unesa.utils.html2text
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.soalViewModel

class TestKarakter : AppCompatActivity(), SQLITEJawabanAdapter.AdapterCallback {
    var mAdapter: SQLITEJawabanAdapter? = null
    lateinit var binding : ActivityTestKarakterBinding
    lateinit var dialog : Dialog
    var halaman = 0
    val databaseHandler = DatabaseHandler(this@TestKarakter)
    var fn_examid = ""
    var nilai = ""
    var id_jawaban : String? = null
    var id_soal : String? = null
    var click : String? = null
    var jawabanList = ArrayList<Soal>()
    lateinit var vm : soalViewModel
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestKarakterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(soalViewModel::class.java)

        sharedPreferences = getSharedPreferences("App",Context.MODE_PRIVATE)
        dialog = progressDialog(this@TestKarakter)
        tampil_soal()

        binding.ulang.setOnRefreshListener {
            binding.ulang.setRefreshing(false)
        }
        binding.ivBack.setOnClickListener { finish() }
        binding.btJawaban.setOnClickListener {
            if (id_jawaban == null){
                val builder = AlertDialog.Builder(this@TestKarakter)
                builder.setTitle("Peringatan")
                builder.setMessage("Jawaban tidak boleh kosong")
                builder.setPositiveButton("Ya") { dialog, which ->
                    dialog.dismiss()
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }else{
               val cek = databaseHandler.CountSoal()
                if (cek.count > 0){
                    cek.moveToFirst()
                    if(halaman-1 >= cek.getString(0).toInt()){
                        vm.rekapaktif(this)
                        startActivity(Intent(this@TestKarakter,RekapKarakter::class.java))
                        finish()
                    }else{
                        val hasil = Jawaban(id_soal,sharedPreferences.getString("fn_userid", null).toString(),id_jawaban,nilai)
                        databaseHandler.AddJawaban(hasil)
                        id_jawaban = null
                        id_soal = null
                        click = null
                        tampil_soal()
                    }
                }
                cek.close()
            }
        }

    }
    private fun tampil_soal (){
        val ok = databaseHandler.getAllSoal(halaman.toString())
        val total = databaseHandler.CountSoal()
        if(ok.count > 0) {
            ok.moveToFirst()
            total.moveToFirst()
            halaman++
            jawabanList.clear()
            val colorValue = ContextCompat.getColor(this@TestKarakter, R.color.text_shadow_light)
            binding.btJawaban.setBackgroundColor(colorValue)
            binding.urutan.text = "$halaman / ${total.getString(0)}"
            if(halaman == total.getString(0).toInt()){
                binding.btJawaban.text = "Selesai"
            }
            fn_examid = ok.getString(0)
            tampil_jawaban(fn_examid)
            binding.textsoal.text = html2text(
                HtmlCompat.fromHtml(ok.getString(4), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString())

        }else{
            vm.rekapaktif(this)
            startActivity(Intent(this@TestKarakter,RekapKarakter::class.java))
            finish()
        }
        ok.close()
        total.close()
    }

    private fun tampil_jawaban (fn_examid : String){
        var cursor: Cursor
        cursor = databaseHandler.getAllJawaban(fn_examid)
        if(cursor.count > 0) {
            cursor.moveToFirst()
            do{
                val jawaban = Soal()
                jawaban.fn_answersid = cursor.getString(0)
                jawaban.fn_scoretypeid = cursor.getString(1)
                jawaban.fv_titleanswers = cursor.getString(2)
                jawaban.fv_descanswers = cursor.getString(3)
                jawaban.fn_value = cursor.getString(4)
                jawabanList.add(jawaban)
            }while(cursor.moveToNext())

            val linearLayoutManager = LinearLayoutManager(this@TestKarakter, LinearLayoutManager.VERTICAL, false)
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
            mAdapter = SQLITEJawabanAdapter(this@TestKarakter,jawabanList,this@TestKarakter)
            binding.tampil.setLayoutManager(linearLayoutManager)
            binding.tampil.setAdapter(mAdapter)
            mAdapter!!.notifyDataSetChanged()

        }else{
            vm.rekapaktif(this)
            startActivity(Intent(this@TestKarakter,RekapKarakter::class.java))
            finish()
        }
        cursor.close()
    }

    override fun onRowAdapterClicked(position: Soal) {
        id_soal = position.fn_scoretypeid.toString()
        id_jawaban = position.fn_answersid.toString()
        nilai = position.fn_value.toString()
        val colorValue = ContextCompat.getColor(this@TestKarakter, R.color.colorPrimaryVariant)
        binding.btJawaban.setBackgroundColor(colorValue)
    }
}