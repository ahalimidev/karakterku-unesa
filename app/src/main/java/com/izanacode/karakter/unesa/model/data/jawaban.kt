package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class jawaban {

    @SerializedName("fn_answersid")
    @Expose
    var fn_answersid: Int? = null

    @SerializedName("fn_examid")
    @Expose
    var fn_examid: String? = null


    @SerializedName("fv_titleanswers")
    @Expose
    var fv_titleanswers: String? = null

    @SerializedName("fv_descanswers")
    @Expose
    var fv_descanswers: String? = null

    @SerializedName("fn_value")
    @Expose
    var fn_value: String? = null
}
