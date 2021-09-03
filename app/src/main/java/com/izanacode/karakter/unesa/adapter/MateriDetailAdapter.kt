package com.izanacode.karakter.unesa.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.model.data.materidetail
import com.izanacode.karakter.unesa.utils.html2text
import java.util.*


class MateriDetailAdapter(private val context: Context, results: ArrayList<materidetail>) :
    RecyclerView.Adapter<MateriDetailAdapter.ItemViewHolder>() {

    private var Items = ArrayList<materidetail>()

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kode :TextView
        val title :TextView
        val desc :TextView


        init {
            kode = itemView.findViewById(R.id.tv_cmd_kode)
            title = itemView.findViewById(R.id.tv_cmd_title)
            desc = itemView.findViewById(R.id.tv_cmd_desc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_materi_detail, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.kode.text = result.fv_codescoretype
        myHolder.title.text = result.fv_namescoretype
        myHolder.desc.text =  html2text(
            HtmlCompat.fromHtml(result.fv_descscoretype.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString())

    }

    override fun getItemCount(): Int {
        return Items.size
    }
}