package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.izanacode.karakter.unesa.databinding.ActivityEditProfilBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.userViewModel
import kotlinx.coroutines.launch

class EditProfil : AppCompatActivity() {
    lateinit var binding : ActivityEditProfilBinding
    lateinit var vm: userViewModel
    lateinit var dialog : Dialog
    var jk = "L"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(userViewModel::class.java)
        dialog = progressDialog(this@EditProfil)
        tampil()
        binding.ivBack.setOnClickListener { finish() }
        binding.rbJk.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.rbL.id){
                jk = "L"
            }else{
                jk = "P"
            }
        }
        binding.btSimpan.setOnClickListener {
           if (binding.etNama.text.toString().isNullOrEmpty()) {
                binding.etNama.requestFocus()
                binding.etNama.error = "Kosong"
           }else if (binding.etNik.text.toString().isNullOrEmpty()) {
               binding.etNik.requestFocus()
               binding.etNik.error = "Kosong"
            }else if (binding.etEmail.text.toString().isNullOrEmpty()) {
                binding.etEmail.requestFocus()
                binding.etEmail.error = "Kosong"
            }else if (binding.etTelepon.text.toString().isNullOrEmpty()) {
                binding.etTelepon.requestFocus()
                binding.etTelepon.error = "Kosong"
            }else if (binding.etEmail.text.toString().isNullOrEmpty()) {
                binding.etEmail.requestFocus()
                binding.etEmail.error = "Kosong"
            }else{
               lifecycleScope.launch {
                   dialog.show()
                   try {
                       val authResponse = vm.edit_profil(this@EditProfil,
                           binding.etNama.text.toString(),
                           binding.etNik.text.toString(),
                           jk,
                           binding.etEmail.text.toString(),
                           binding.etTelepon.text.toString()
                           )
                       if (authResponse.body()!!.success == 1) {
                           dialog.dismiss()
                           finish()
                       } else {
                           dialog.dismiss()
                           Toast.makeText(
                               this@EditProfil,
                               authResponse.body()!!.message,
                               Toast.LENGTH_SHORT
                           ).show()
                       }
                   } catch (throwable: Throwable) {
                       dialog.dismiss()
                       Log.e("ERROR", throwable.toString())
                   }
               }
            }
        }
    }
    private  fun tampil(){
        lifecycleScope.launch {
            dialog.show()
            try {
                val authResponse = vm.profil(this@EditProfil)
                if (authResponse.body()!!.success == 1) {
                    dialog.dismiss()
                    val result =authResponse.body()!!.data
                    binding.etNama.setText(result.fv_name)
                    binding.etNik.setText(result.fv_nik)
                    if(result.fv_gender == "L"){
                        binding.rbL.isChecked = true
                    }else{
                        binding.rbP.isChecked = true

                    }
                    binding.etEmail.setText(result.fv_email)
                    binding.etTelepon.setText(result.fv_telepon)
                } else {
                    dialog.dismiss()
                    Toast.makeText(
                        this@EditProfil,
                        authResponse.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (throwable: Throwable) {
                dialog.dismiss()
                Log.e("ERROR", throwable.toString())
            }
        }
    }
}