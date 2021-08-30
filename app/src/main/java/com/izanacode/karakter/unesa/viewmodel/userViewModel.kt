package com.izanacode.karakter.unesa.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.izanacode.karakter.unesa.model.data.user
import com.izanacode.karakter.unesa.server.Api
import com.izanacode.karakter.unesa.server.retrofitFatory
import com.izanacode.karakter.unesa.view.Login
import com.izanacode.karakter.unesa.view.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class userViewModel : ViewModel() {
    var api: Api = retrofitFatory.retrofitService()
    suspend fun loginuser(
        username: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        api.login(username, password)
    }

    fun setLogin(activity: Activity, context: Context, dataUser: user) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("fn_userid", dataUser.fn_userid)
        editor.putString("fv_nim", dataUser.fv_nik)
        editor.putString("fv_name", dataUser.fv_name)
        editor.putString("fv_gender", dataUser.fv_gender)
        editor.putString("fv_email", dataUser.fv_email)
        editor.putString("fv_telepon", dataUser.fv_telepon)
        editor.putString("fv_picture", dataUser.fv_picture)
        editor.putBoolean("login", true)
        editor.commit()
        activity.startActivity(Intent(context, Menu::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        activity.finish()
    }

    suspend fun daftar(
        fv_nik: RequestBody,
        fv_username: RequestBody,
        fv_password: RequestBody,
        fv_name: RequestBody,
        fv_gender: RequestBody,
        fv_telepon: RequestBody,
        fv_email: RequestBody,
        fv_picture:  MultipartBody.Part,
    ) = withContext(Dispatchers.IO) {
        api.daftar(fv_nik,fv_username,fv_password,fv_name,fv_gender,fv_telepon,fv_email,fv_picture)
    }

    fun getLogin(activity: Activity, context: Context) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("login", false) == true) {
            activity.startActivity(Intent(context, Menu::class.java))
            activity.finish()
        }else{
            Handler().postDelayed({
                activity.startActivity(Intent(context, Login::class.java))
                activity.finish()
            }, 5000)
        }
    }

    fun Logout(activity: Activity, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Peringatan")
        builder.setMessage("Apakah kamu yakin keluar aplikasi?")
        builder.setPositiveButton("Ya") { dialog, which ->
            dialog.dismiss()
            val sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            activity.startActivity(
                Intent(
                    context,
                    Login::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            activity.finish()
        }
        builder.setNegativeButton("Tidak") { dialog, which ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    suspend fun profil(context: Context) = withContext(Dispatchers.Main) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        api.profil(sharedPreferences.getString("fn_userid", null).toString())
    }
    fun nama(context: Context) : String  {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
         return sharedPreferences.getString("fv_name", null).toString()
    }

    suspend fun ganti_password(
        context: Context,
        fv_password: String,
    ) = withContext(Dispatchers.IO) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        api.ganti_password(sharedPreferences.getString("fn_userid", null).toString(),fv_password)
    }

    suspend fun edit_profil(
        context: Context,
        fv_name: String,
        fv_nik: String,
        fv_gender: String,
        fv_email: String,
        fv_telepon: String,
    ) = withContext(Dispatchers.IO) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        api.profil_edit(sharedPreferences.getString("fn_userid", null).toString(),fv_name,fv_nik,fv_gender,fv_email,fv_telepon)
    }
    suspend fun upload(
        context: Context,
        fv_picture:  MultipartBody.Part,
    ) = withContext(Dispatchers.IO) {
        var sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
        api.upload(RequestBody.create("text/plain".toMediaTypeOrNull(),sharedPreferences.getString("fn_userid", null).toString()),fv_picture)
    }
}

