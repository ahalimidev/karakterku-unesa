package com.izanacode.karakter.unesa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carbon.widget.TextView
import com.izanacode.karakter.model.Rekap
import com.izanacode.karakter.unesa.R
import java.util.*


class SQLITEJRekapAdapter(private val context: Context, results: ArrayList<Rekap>) : RecyclerView.Adapter<SQLITEJRekapAdapter.ItemViewHolder>() {

    private var Items = ArrayList<Rekap>()
    init {
        this.Items = results
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val kompetensi : TextView = itemView.findViewById(R.id.tv_crk_kompetensi)
        val nilai : TextView = itemView.findViewById(R.id.tv_crk_nilai)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_rekap_karakter, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.kompetensi.text = result.fv_nametoc
        myHolder.nilai.text = result.hasil

    }

    override fun getItemCount(): Int {
        return Items.size
    }
}