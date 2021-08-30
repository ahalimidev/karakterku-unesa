package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class berita{
    @SerializedName("fn_newsid")
    @Expose
    var fn_newsid: String? = null

    @SerializedName("fn_tocid")
    @Expose
    var fv_codetoc: String? = null

    @SerializedName("fv_nametoc")
    @Expose
    var fv_nametoc: String? = null

    @SerializedName("fv_titlenews")
    @Expose
    var fv_titlenews:String? = null

    @SerializedName("fv_linknews")
    @Expose
    var fv_linknews:String? = null

    @SerializedName("fv_picture")
    @Expose
    var fv_picture: String? = null


}