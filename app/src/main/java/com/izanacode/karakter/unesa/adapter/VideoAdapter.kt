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
import com.izanacode.karakter.unesa.model.data.video
import com.izanacode.karakter.unesa.view.VideoWebview
import java.util.*


class VideoAdapter(private val context: Context, results: ArrayList<video>) :
    RecyclerView.Adapter<VideoAdapter.ItemViewHolder>() {

    private var Items = ArrayList<video>()

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kategori :TextView
        val title :TextView
        val foto :ImageView


        init {
            kategori = itemView.findViewById(R.id.tv_cv_kategori)
            title = itemView.findViewById(R.id.tv_cv_title)
            foto = itemView.findViewById(R.id.iv_cv_foto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_video, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.kategori.text = result.fv_nametoc
        myHolder.title.text = result.fv_titlevideos
        Glide.with(context)
            .load("https://karakterku.com/asset/img/videos/"+result.fv_picture)
            .into(myHolder.foto)
        myHolder.itemView.setOnClickListener {
            context.startActivity(Intent(context,VideoWebview::class.java)
                .putExtra("link",result.fv_linkvideos)
            )
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }
}