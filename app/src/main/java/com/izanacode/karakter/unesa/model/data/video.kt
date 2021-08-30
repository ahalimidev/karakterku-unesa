package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class video{
    @SerializedName("fn_videosid")
    @Expose
    var fn_videosid: String? = null

    @SerializedName("fn_tocid")
    @Expose
    var fv_codetoc: String? = null

    @SerializedName("fv_nametoc")
    @Expose
    var fv_nametoc: String? = null

    @SerializedName("fv_titlevideos")
    @Expose
    var fv_titlevideos:String? = null

    @SerializedName("fv_linkvideos")
    @Expose
    var fv_linkvideos:String? = null

    @SerializedName("fv_picture")
    @Expose
    var fv_picture: String? = null


}