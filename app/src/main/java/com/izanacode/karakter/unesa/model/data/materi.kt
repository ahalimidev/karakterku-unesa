package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class materi{
    @SerializedName("fn_tocid")
    @Expose
    var fn_tocid: String? = null

    @SerializedName("fv_codetoc")
    @Expose
    var fv_codetoc: String? = null

    @SerializedName("fv_nametoc")
    @Expose
    var fv_nametoc:String? = null

    @SerializedName("fv_desctoc")
    @Expose
    var fv_desctoc: String? = null

    @SerializedName("scoretype_tb")
    @Expose
    lateinit var scoretype_tb: ArrayList<materidetail>


}