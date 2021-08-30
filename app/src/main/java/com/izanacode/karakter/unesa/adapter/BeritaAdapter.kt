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
import com.izanacode.karakter.unesa.view.BeritaWebView
import java.util.*


class BeritaAdapter(private val context: Context, results: ArrayList<berita>) :
    RecyclerView.Adapter<BeritaAdapter.ItemViewHolder>() {

    private var Items = ArrayList<berita>()

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kategori :TextView
        val title :TextView
        val foto :ImageView


        init {
            kategori = itemView.findViewById(R.id.tv_cb_kategori)
            title = itemView.findViewById(R.id.tv_cb_title)
            foto = itemView.findViewById(R.id.iv_cb_foto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_berita, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.kategori.text = result.fv_nametoc
        myHolder.title.text = result.fv_titlenews
        Glide.with(context)
            .load("https://karakterku.com/asset/img/news/"+result.fv_picture)
            .into(myHolder.foto)
        myHolder.itemView.setOnClickListener {
            context.startActivity(Intent(context,BeritaWebView::class.java)
                .putExtra("link",result.fv_linknews)
            )
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }
}