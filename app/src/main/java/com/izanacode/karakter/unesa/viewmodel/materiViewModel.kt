package com.izanacode.karakter.unesa.viewmodel

import androidx.lifecycle.ViewModel
import com.izanacode.karakter.unesa.server.Api
import com.izanacode.karakter.unesa.server.retrofitFatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class materiViewModel : ViewModel() {

    var api: Api = retrofitFatory.retrofitService()
    suspend fun materi_tampil() = withContext(Dispatchers.Main) {
        api.materi()
    }
    suspend fun berita_tampil(fn_tocid :String) = withContext(Dispatchers.Main) {
        api.berita(fn_tocid)
    }
    suspend fun video_tampil(fn_tocid :String) = withContext(Dispatchers.Main) {
        api.video(fn_tocid)
    }
    suspend fun dashboard_tampil() = withContext(Dispatchers.Main) {
        api.dashboard()
    }
    suspend fun timpengembang_tampil() = withContext(Dispatchers.Main) {
        api.timpengembang()
    }
    suspend fun materi_tampil_detail(
        fn_tocid : String
    ) = withContext(Dispatchers.Main) {
        api.materi_detail(fn_tocid)
    }

    suspend fun pdf(fn_examresults : String) = withContext(Dispatchers.Main) {
        api.pdf(fn_examresults)
    }
}

