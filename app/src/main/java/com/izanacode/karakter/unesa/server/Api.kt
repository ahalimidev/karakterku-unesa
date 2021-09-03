package com.izanacode.karakter.unesa.server

import com.izanacode.karakter.unesa.model.respon.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface Api {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("fv_username") fv_username: String,
        @Field("fv_password") fv_password: String
    ): Response<user>


    @Multipart
    @POST("user/daftar")
    suspend fun daftar(
        @Part("fv_nik") fv_nik: RequestBody,
        @Part("fv_username") fv_username: RequestBody,
        @Part("fv_password") fv_password: RequestBody,
        @Part("fv_name") fv_name: RequestBody,
        @Part("fv_gender") fv_gender: RequestBody,
        @Part("fv_telepon") fv_telepon: RequestBody,
        @Part("fv_email") fv_email: RequestBody,
        @Part fv_picture : MultipartBody.Part

    ): Response<user>

    @FormUrlEncoded
    @PUT("user/password/{fn_userid}")
    suspend fun ganti_password(
        @Path("fn_userid") fv_username: String,
        @Field("fv_password") fv_password: String
    ): Response<user>

    @GET("user/profil/{fn_userid}")
    suspend fun profil(
        @Path("fn_userid") fn_userid: String,
    ): Response<user>

    @FormUrlEncoded
    @PUT("user/profil/{fn_userid}")
    suspend fun profil_edit(
        @Path("fn_userid") fn_userid: String,
        @Field("fv_name") fv_name: String,
        @Field("fv_nik") fv_nik: String,
        @Field("fv_gender") fv_gender: String,
        @Field("fv_email") fv_email: String,
        @Field("fv_telepon") fv_telepon: String,
    ): Response<user>

    @Multipart
    @POST("user/upload")
    suspend fun upload(
        @Part("fn_userid") fn_userid: RequestBody,
        @Part fv_picture : MultipartBody.Part

    ): Response<user>

    @GET("materi")
    suspend fun materi(): Response<materi>

    @GET("materi/detail/{fn_tocid}")
    suspend fun materi_detail(
        @Path("fn_tocid") fn_tocid :String
    ): Response<materidetail>

    @GET("materi/berita/{fn_tocid}")
    suspend fun berita(
        @Path("fn_tocid") fn_tocid: String
    ): Response<berita>

    @GET("materi/video/{fn_tocid}")
    suspend fun video(
        @Path("fn_tocid") fn_tocid: String
    ): Response<video>

    @GET("desc/dashboard")
    suspend fun dashboard(): Response<dashboard>

    @GET("desc/timpengembang")
    suspend fun timpengembang(): Response<team>


    @GET("data/soal")
    suspend fun datasoal(): Response<soal>

    @FormUrlEncoded
    @POST("data/simpan/jawaban")
    suspend fun simpanjawaban(
        @Field("data_json") data_json: String
    ): Response<respon>

    @GET("data/tampil/rekap/{fn_userid}")
    suspend fun tampilrekap(
        @Path("fn_userid") fn_userid : String
    ): Response<histori>

    @GET("data/tampil/jawaban/{fn_examresults}")
    suspend fun tampiljawaban(
        @Path("fn_examresults") fn_examresults : String
    ): Response<histori_detail>

    @GET("/data/cari/user/{fn_examresults}")
    suspend fun jawaban_user(
        @Path("fn_examresults") fn_examresults : String
    ): Response<rekap>

    @GET("/data/pdf/{fn_examresults}")
    suspend fun pdf(
        @Path("fn_examresults") fn_examresults : String
    ): Response<pdf>
}