package com.izanacode.karakter.unesa.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.model.data.materi
import com.izanacode.karakter.unesa.utils.html2text
import com.izanacode.karakter.unesa.view.MateriDetail
import java.util.*


class MateriAdapter(private val context: Context, results: ArrayList<materi>) :
    RecyclerView.Adapter<MateriAdapter.ItemViewHolder>() {

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
            kode = itemView.findViewById(R.id.tv_cm_kode)
            title = itemView.findViewById(R.id.tv_cm_title)
            desc = itemView.findViewById(R.id.tv_cm_desc)
            lanjut = itemView.findViewById(R.id.ll_cm_lanjut)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_materi, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.kode.text = result.fv_codetoc
        myHolder.title.text = result.fv_nametoc
        myHolder.desc.text = html2text(HtmlCompat.fromHtml(result.fv_desctoc.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY)
            .toString())
        myHolder.lanjut.setOnClickListener {
            context.startActivity(Intent(context,MateriDetail::class.java)
                .putExtra("fn_tocid",result.fn_tocid)
                .putExtra("fv_nametoc",result.fv_nametoc)
                .putExtra("fv_desctoc",result.fv_desctoc)
            )
        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }
}