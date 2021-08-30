package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.izanacode.karakter.unesa.model.data.rekaphasil
import java.util.ArrayList

class histori{
    @SerializedName("fn_examresults")
    @Expose
    var fn_examresults: String? = null

    @SerializedName("fv_date")
    @Expose
    var fv_date: String? = null

    @SerializedName("rekap")
    @Expose
    var rekap: ArrayList<rekaphasil>? = null


}