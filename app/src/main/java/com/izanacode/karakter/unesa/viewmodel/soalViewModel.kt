package com.izanacode.karakter.unesa.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.izanacode.karakter.unesa.server.Api
import com.izanacode.karakter.unesa.server.retrofitFatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class soalViewModel : ViewModel() {
    var api: Api = retrofitFatory.retrofitService()

    suspend fun datasoal() = withContext(Dispatchers.Main) {
        api.datasoal()
    }

    fun rekapaktif(context: Context) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("rekap", true)
        editor.commit()

    }
    fun rekapselesai(context: Context) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("rekap", false)
        editor.commit()

    }
    fun rekap( context: Context): Boolean {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("rekap", false)
    }
    suspend fun simpanjawaban(data : String) = withContext(Dispatchers.IO) {
        api.simpanjawaban(data)
    }
    suspend fun tampilrekap(context: Context) = withContext(Dispatchers.Main) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        api.tampilrekap(sharedPreferences.getString("fn_userid", "").toString())
    }

    suspend fun tampiljawaban(fn_examresults: String) = withContext(Dispatchers.Main) {
        api.tampiljawaban(fn_examresults)
    }
}

