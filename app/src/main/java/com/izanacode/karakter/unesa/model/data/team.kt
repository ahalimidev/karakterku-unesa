package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class team{
    @SerializedName("fn_draftid")
    @Expose
    var fn_draftid: String? = null

    @SerializedName("fv_code")
    @Expose
    var fv_code: String? = null

    @SerializedName("fv_name")
    @Expose
    var fv_name: String? = null

    @SerializedName("fv_picture")
    @Expose
    var fv_picture: String? = null



}