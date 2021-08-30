package com.izanacode.karakter.unesa.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class user{
    @SerializedName("fn_userid")
    @Expose
    var fn_userid: String? = null

    @SerializedName("fv_name")
    @Expose
    var fv_name: String? = null

    @SerializedName("fv_nik")
    @Expose
    var fv_nik: String? = null

    @SerializedName("fv_gender")
    @Expose
    var fv_gender:String? = null

    @SerializedName("fv_email")
    @Expose
    var fv_email: String? = null

    @SerializedName("fv_telepon")
    @Expose
    var fv_telepon: String? = null

    @SerializedName("fv_picture")
    @Expose
    var fv_picture: String? = null

    @SerializedName("fv_desc")
    @Expose
    var fv_desc: String? = null

}