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
import java.util.*


class HistoriDetailAdapter(private val context: Context, results: ArrayList<histori_detail>) :
    RecyclerView.Adapter<HistoriDetailAdapter.ItemViewHolder>() {

    private var Items = ArrayList<histori_detail>()
    private var rAdapter : HistoriDetailJawabanAdapter? = null

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fv_nametoc :TextView
        val tampil :RecyclerView



        init {
            fv_nametoc = itemView.findViewById(R.id.fv_nametoc)
            tampil = itemView.findViewById(R.id.rv_csh_tampil)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_soal_history, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.fv_nametoc.text = "${result.fv_nametoc}"
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPositionWithOffset(0, 0)
        rAdapter = result.jawaban?.let { HistoriDetailJawabanAdapter(context, it) }
        myHolder.tampil.setLayoutManager(linearLayoutManager)
        myHolder.tampil.setAdapter(rAdapter)
        rAdapter!!.notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return Items.size
    }
}