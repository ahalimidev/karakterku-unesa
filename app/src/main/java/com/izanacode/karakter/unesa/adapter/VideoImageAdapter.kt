package com.izanacode.karakter.unesa.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.model.data.berita
import com.izanacode.karakter.unesa.model.data.video
import com.izanacode.karakter.unesa.view.BeritaWebView
import java.util.*


class VideoImageAdapter(private val context: Context, results: ArrayList<video>) :
    RecyclerView.Adapter<VideoImageAdapter.ItemViewHolder>() {

    private var Items = ArrayList<video>()

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val foto :ImageView


        init {

            foto = itemView.findViewById(R.id.iv_cf_foto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_fofo, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        Glide.with(context)
            .load("https://karakterku.com/asset/img/news/"+result.fv_picture)
            .into(myHolder.foto)
    }

    override fun getItemCount(): Int {
        return Items.size
    }
}