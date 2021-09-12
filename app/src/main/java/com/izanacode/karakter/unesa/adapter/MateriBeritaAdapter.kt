package com.izanacode.karakter.unesa.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.model.data.materi
import com.izanacode.karakter.unesa.utils.html2text
import com.izanacode.karakter.unesa.view.MateriDetail
import com.izanacode.karakter.unesa.view.berita
import java.util.*


class MateriBeritaAdapter(private val context: Context, results: ArrayList<materi>) :
    RecyclerView.Adapter<MateriBeritaAdapter.ItemViewHolder>() {

    private var Items = ArrayList<materi>()

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kode :TextView
        val title :TextView
        val desc :TextView
        val lanjut : LinearLayout



        init {
            kode = itemView.findViewById(R.id.tv_cmb_kode)
            title = itemView.findViewById(R.id.tv_cmb_title)
            desc = itemView.findViewById(R.id.tv_cmb_desc)
            lanjut = itemView.findViewById(R.id.ll_cmb_lanjut)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_materi_berita, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val result = Items[position]
        holder.kode.text = result.fv_codetoc
        holder.title.text = result.fv_nametoc
        holder.desc.text = html2text(
            HtmlCompat.fromHtml(result.fv_desctoc.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
        )
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        linearLayoutManager.scrollToPositionWithOffset(0, 0)

        holder.lanjut.setOnClickListener {
            context.startActivity(
                Intent(context, berita::class.java)
                    .putExtra("fn_tocid", result.fn_tocid)
                    .putExtra("fv_nametoc", result.fv_nametoc)

            )
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }
}