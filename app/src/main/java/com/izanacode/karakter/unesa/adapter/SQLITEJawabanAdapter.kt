package com.izanacode.karakter.unesa.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.model.Soal
import com.izanacode.karakter.unesa.utils.html2text
import java.util.*


class SQLITEJawabanAdapter(private val context: Context, results: ArrayList<Soal>, adapterCallback: AdapterCallback) : RecyclerView.Adapter<SQLITEJawabanAdapter.ItemViewHolder>() {

    private var Items = ArrayList<Soal>()
    private var mAdapterCallback: AdapterCallback? = null
    private var checkedPosition = -1

    init {
        this.Items = results
        this.mAdapterCallback = adapterCallback

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val pilih : carbon.widget.RadioButton = itemView.findViewById(R.id.rb_cj_jawban)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_jawaban, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]

        myHolder.pilih.text =   html2text(
            HtmlCompat.fromHtml(result.fv_descanswers.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString())

        if (checkedPosition == -1) {
            myHolder.pilih.setChecked(false);
        } else {
            if (checkedPosition == position) {
                myHolder.pilih.setChecked(true);
            } else {
                myHolder.pilih.setChecked(false);
            }
        }
        myHolder.pilih.setOnClickListener {
            checkedPosition = holder.adapterPosition
            notifyDataSetChanged()
            mAdapterCallback!!.onRowAdapterClicked(result)
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }
    interface AdapterCallback {
        /*
        Disini kalian bisa membuat beberapa fungsi dengan parameter sesuai kebutuhan. Kebutuhan
        disini adalah untuk mendapatkan pada posisi mana user mengklik listnya.
         */
        fun onRowAdapterClicked(position: Soal)
    }
}