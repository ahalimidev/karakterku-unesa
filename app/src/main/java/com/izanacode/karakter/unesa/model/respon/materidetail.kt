package com.izanacode.karakter.unesa.model.respon

import com.izanacode.karakter.unesa.model.data.materidetail
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.izanacode.karakter.unesa.model.data.materi

class materidetail {
    @SerializedName("success")
    @Expose
    var success: Int = 0

    @SerializedName("message")
    @Expose
    lateinit var message: String

    @SerializedName("data")
    @Expose
    lateinit var data: ArrayList<materidetail>
}