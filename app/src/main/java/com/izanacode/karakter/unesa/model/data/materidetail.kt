package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class materidetail{
    @SerializedName("fn_scoretypeid")
    @Expose
    var fn_scoretypeid: String? = null

    @SerializedName("fn_tocid")
    @Expose
    var fn_tocid: String? = null

    @SerializedName("fv_codescoretype")
    @Expose
    var fv_codescoretype: String? = null

    @SerializedName("fv_namescoretype")
    @Expose
    var fv_namescoretype:String? = null

    @SerializedName("fv_descscoretype")
    @Expose
    var fv_descscoretype: String? = null

    @SerializedName("examanswers_tb")
    @Expose
    lateinit var examanswers_tb: ArrayList<soal>

}