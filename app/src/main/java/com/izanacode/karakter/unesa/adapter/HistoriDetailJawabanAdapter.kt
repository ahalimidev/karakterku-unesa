package com.izanacode.karakter.unesa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.model.data.histori_detail
import com.izanacode.karakter.unesa.model.data.jawaban_detail
import java.util.*


class HistoriDetailJawabanAdapter(private val context: Context, results: ArrayList<jawaban_detail>) :
    RecyclerView.Adapter<HistoriDetailJawabanAdapter.ItemViewHolder>() {

    private var Items = ArrayList<jawaban_detail>()
    private var nomor = 1
    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_cshd_nomor :TextView
        val tv_cshd_detail :TextView



        init {
            tv_cshd_nomor = itemView.findViewById(R.id.tv_cshd_nomor)
            tv_cshd_detail = itemView.findViewById(R.id.tv_cshd_detail)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_soal_history_detail, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.tv_cshd_nomor.text = (nomor++).toString()
        myHolder.tv_cshd_detail.text = result.fv_descanswers


    }

    override fun getItemCount(): Int {
        return Items.size
    }
}