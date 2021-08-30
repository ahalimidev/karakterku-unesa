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
import com.izanacode.karakter.unesa.model.data.team
import com.izanacode.karakter.unesa.view.BeritaWebView
import java.util.*


class TeamAdapter(private val context: Context, results: ArrayList<team>) :
    RecyclerView.Adapter<TeamAdapter.ItemViewHolder>() {

    private var Items = ArrayList<team>()

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val code :TextView
        val nama :TextView
        val foto :ImageView


        init {
            code = itemView.findViewById(R.id.tv_ct_kode)
            nama = itemView.findViewById(R.id.tv_ct_nama)
            foto = itemView.findViewById(R.id.iv_ct_foto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_team, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.code.text = result.fv_code
        myHolder.nama.text = result.fv_name
        Glide.with(context)
            .load("https://karakterku.com/asset/img/team/"+result.fv_picture)
            .into(myHolder.foto)
    }

    override fun getItemCount(): Int {
        return Items.size
    }
}