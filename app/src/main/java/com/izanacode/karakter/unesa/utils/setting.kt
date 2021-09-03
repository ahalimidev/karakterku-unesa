package com.izanacode.karakter.unesa.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.izanacode.karakter.unesa.R
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import org.jsoup.Jsoup


fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}
fun progressDialog(context: Context): Dialog {

    val dialog = Dialog(context)
    val inflate = LayoutInflater.from(context).inflate(R.layout.progress, null)
    dialog.setContentView(inflate)
    dialog.setCancelable(false)
    dialog.window!!.setBackgroundDrawable(
        ColorDrawable(Color.TRANSPARENT)
    )
    return dialog
}

fun tidakada(data :String, context: Context){
    val builder = AlertDialog.Builder(context)
    builder.setMessage("Data $data Masih Belum Ada...")
    builder.setPositiveButton("ok") { dialog, which ->
        dialog.dismiss()
    }
    val alertDialog = builder.create()
    alertDialog.show()
}


fun html2text(html: String?): String? {
    return Jsoup.parse(html).text()
}