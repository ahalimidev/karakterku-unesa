package com.izanacode.karakter.unesa.model.respon

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class respon {
    @SerializedName("success")
    @Expose
    var success: Int = 0

    @SerializedName("data")
    @Expose
    var data : String? = null


    @SerializedName("message")
    @Expose
    lateinit var message: String
}