package com.izanacode.karakter.unesa.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.izanacode.karakter.unesa.databinding.ActivityLupaPasswordBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.userViewModel
import kotlinx.coroutines.launch

class LupaPassword : AppCompatActivity() {
    lateinit var binding : ActivityLupaPasswordBinding
    lateinit var vm: userViewModel
    lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(userViewModel::class.java)
        dialog = progressDialog(this@LupaPassword)
        binding.ivBack.setOnClickListener {
            finish()

        }
        binding.btGantiPassword.setOnClickListener {
            if(binding.etPassword.text.toString().isEmpty()){
                binding.etPassword.requestFocus()
                binding.etPassword.error = "Kosong"
            }else if (binding.etPasswordUlang.text.toString().isEmpty()){
                binding.etPasswordUlang.requestFocus()
                binding.etPasswordUlang.error = "Kosong"
            }else if(binding.etPassword.text.toString().equals(binding.etPasswordUlang.text.toString())){
                lifecycleScope.launch {
                    dialog.show()
                    try {
                        val authResponse = vm.ganti_password(this@LupaPassword,binding.etPassword.text.toString())
                        if (authResponse.body()!!.success == 1) {
                            dialog.dismiss()
                            finish()
                        } else {
                            dialog.dismiss()
                            Toast.makeText(
                                this@LupaPassword,
                                authResponse.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (throwable: Throwable) {
                        dialog.dismiss()
                        Log.e("ERROR", throwable.toString())
                    }
                }
            }else{
                binding.etPasswordUlang.requestFocus()
                binding.etPasswordUlang.error = "Tidak sesuai"
            }
        }
    }

}