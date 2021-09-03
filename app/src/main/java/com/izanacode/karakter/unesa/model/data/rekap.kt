package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class rekap{

    @SerializedName("fv_nametoc")
    @Expose
    var fv_nametoc: String? = null

    @SerializedName("hasil")
    @Expose
    var hasil: String? = null

}