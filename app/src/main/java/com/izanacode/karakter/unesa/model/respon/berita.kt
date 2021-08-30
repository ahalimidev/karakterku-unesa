package com.izanacode.karakter.unesa.model.respon

import com.izanacode.karakter.unesa.model.data.berita
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class berita {
    @SerializedName("success")
    @Expose
    var success: Int = 0

    @SerializedName("message")
    @Expose
    lateinit var message: String

    @SerializedName("data")
    @Expose
    lateinit var data: ArrayList<berita>
}