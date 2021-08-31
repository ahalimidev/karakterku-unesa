package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.izanacode.karakter.unesa.model.data.rekaphasil
import java.util.ArrayList

class histori_detail{
    @SerializedName("fv_nametoc")
    @Expose
    var fv_nametoc: String? = null


    @SerializedName("jawaban")
    @Expose
    var jawaban: ArrayList<jawaban_detail>? = null

}