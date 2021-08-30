package com.izanacode.karakter.unesa.server

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.izanacode.karakter.unesa.model.*

class DatabaseHandler (context: Context) : SQLiteOpenHelper(context, "database", null, 3) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_MATERI = "CREATE TABLE materi ( fn_tocid TEXT, fv_codetoc TEXT, fv_nametoc TEXT,fv_desctoc TEXT);"
        val CREATE_TABLE_MATERI_DETAIL = "CREATE TABLE materi_detail ( fn_scoretypeid TEXT, fn_tocid TEXT, fv_codescoretype TEXT, fv_namescoretype TEXT, fv_descscoretype TEXT);"
        val CREATE_TABLE_SOAL = "CREATE TABLE soal ( fn_answersid TEXT, fn_scoretypeid TEXT, fv_titleanswers TEXT, fv_descanswers TEXT, fn_value TEXT);"
        val CREATE_TABLE_JAWABAN = "CREATE TABLE jawaban (fn_scoretypeid TEXT, fn_userid TEXT, fn_answersid TEXT, fn_value TEXT);"

        db?.execSQL(CREATE_TABLE_MATERI)
        db?.execSQL(CREATE_TABLE_MATERI_DETAIL)
        db?.execSQL(CREATE_TABLE_SOAL)
        db?.execSQL(CREATE_TABLE_JAWABAN)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS materi")
        db!!.execSQL("DROP TABLE IF EXISTS materi_detail")
        db!!.execSQL("DROP TABLE IF EXISTS soal")
        db!!.execSQL("DROP TABLE IF EXISTS jawaban")
        onCreate(db)
    }


    fun AddMateri (materi: Materi): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("fn_tocid", materi.fn_tocid)
        values.put("fv_codetoc", materi.fv_codetoc)
        values.put("fv_nametoc", materi.fv_nametoc)
        values.put("fv_desctoc", materi.fv_desctoc)
        val _success = db.insert("materi", null, values)
        return (("$_success").toInt() != -1)
    }
    fun AddMateriDetail (materidetail: MateriDetail): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("fn_scoretypeid", materidetail.fn_scoretypeid)
        values.put("fn_tocid", materidetail.fn_tocid)
        values.put("fv_codescoretype", materidetail.fv_codescoretype)
        values.put("fv_namescoretype", materidetail.fv_namescoretype)
        values.put("fv_descscoretype", materidetail.fv_descscoretype)
        val _success = db.insert("materi_detail", null, values)
        return (("$_success").toInt() != -1)
    }

    fun AddSoal (soal: Soal): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("fn_answersid", soal.fn_answersid)
        values.put("fn_scoretypeid", soal.fn_scoretypeid)
        values.put("fv_titleanswers", soal.fv_titleanswers)
        values.put("fv_descanswers", soal.fv_descanswers)
        values.put("fn_value", soal.fn_value)
        val _success = db.insert("soal", null, values)
        return (("$_success").toInt() != -1)
    }

    fun AddJawaban (jawaban: Jawaban): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("fn_scoretypeid", jawaban.fn_scoretypeid)
        values.put("fn_userid", jawaban.fn_userid)
        values.put("fn_answersid", jawaban.fn_answersid)
        values.put("fn_value", jawaban.fn_value)
        val _success = db.insert("jawaban", null, values)
        return (("$_success").toInt() != -1)
    }

    fun CountSoal(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT count(fn_scoretypeid) as total FROM materi_detail ", null)
    }

    fun getAllSoal(halaman : String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM materi_detail LIMIT $halaman, 1", null)
    }

    fun getAllJawaban(fn_scoretypeid : String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM soal where fn_scoretypeid ='$fn_scoretypeid' ", null)
    }

    fun Rekap(fn_userid : String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT materi.fn_tocid,materi.fv_nametoc,avg(jawaban.fn_value) as hasil FROM jawaban\n" +
                "LEFT JOIN materi_detail on jawaban.fn_scoretypeid = materi_detail.fn_scoretypeid\n" +
                "LEFT JOIN materi on materi.fn_tocid = materi_detail.fn_tocid\n" +
                "WHERE jawaban.fn_userid = '$fn_userid'\n" +
                "GROUP BY materi.fv_nametoc", null)
    }
    fun tampiljawaban(fn_userid : String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM jawaban where fn_userid ='$fn_userid' ", null)
    }

    fun detele() : Boolean{
        val db = this.writableDatabase
        db.execSQL("DELETE FROM  materi")
        db.execSQL("DELETE FROM  materi_detail")
        db.execSQL("DELETE FROM  soal")
        db.execSQL("DELETE FROM  jawaban")
        db.close()
        return true
    }

}