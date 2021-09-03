package com.izanacode.karakter.unesa.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import carbon.widget.Button
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.model.data.histori
import com.izanacode.karakter.unesa.view.HistoryDetail
import java.util.*
import com.github.mikephil.charting.components.Legend
import com.izanacode.karakter.unesa.view.pdf
import com.izanacode.karakter.unesa.viewmodel.materiViewModel
import com.izanacode.karakter.unesa.viewmodel.soalViewModel
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat


class HistoriAdapter(private val context: Context, results: ArrayList<histori>) :
    RecyclerView.Adapter<HistoriAdapter.ItemViewHolder>() {

    private var Items = ArrayList<histori>()
    lateinit var vm : materiViewModel

    init {
        this.Items = results

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tanggal :TextView
        val pdf : Button
        val detail : Button
        val chart: PieChart


        init {
            tanggal = itemView.findViewById(R.id.tv_chk_tanggal)
            detail = itemView.findViewById(R.id.bt_chk_hasil)
            pdf = itemView.findViewById(R.id.bt_chk_pdf)
            chart = itemView.findViewById(R.id.fragment_verticalbarchart_chart);

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costum_hasil_karakter, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder

        val result = Items[position]

        myHolder.tanggal.text = "Tanggal : ${result.fv_date}"
        myHolder.detail.setOnClickListener {
            context.startActivity(Intent(context, HistoryDetail::class.java)
                .putExtra("fn_examresults",result.fn_examresults)
            )
        }
        myHolder.pdf.setOnClickListener {
            if(checkAndRequestPermissions()){
                context.startActivity(Intent(context,pdf::class.java).putExtra("fn_examresults",result.fn_examresults))
            }else{
                checkAndRequestPermissions()
            }
        }

        //pupulating list of PieEntires
        val pieEntires: MutableList<PieEntry> = ArrayList()
        for (i in 0 until result.rekap!!.size) {
            pieEntires.add(PieEntry(result.rekap!![i].hasil!!.toFloat(),result.rekap!![i].fv_nametoc!!.toString()))
        }
        Log.e("DDDDDD", pieEntires.toString())
        val dataSet = PieDataSet(pieEntires, "")
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        myHolder.chart.data = data

        myHolder.chart.description.isEnabled = false
        myHolder.chart.isDrawHoleEnabled = true
        myHolder.chart.transparentCircleRadius = 50f
        myHolder.chart.holeRadius = 50f
        myHolder.chart.invalidate()
        myHolder.chart.setDrawEntryLabels(false)
        myHolder.chart.setDrawMarkers(true)
        myHolder.chart.rotationAngle = 0F
        myHolder.chart.isRotationEnabled = true
        myHolder.chart.maxHighlightDistance = 20f
        myHolder.chart.legend.isWordWrapEnabled = true
        myHolder.chart.isHighlightPerTapEnabled = true
        myHolder.chart.legend.form = Legend.LegendForm.CIRCLE
        data.setValueTextSize(13f)


    }
    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val storage = ContextCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                context as Activity,
                listPermissionsNeeded.toTypedArray(),
                1
            )
            return false
        }
        return true
    }
    override fun getItemCount(): Int {
        return Items.size
    }
}