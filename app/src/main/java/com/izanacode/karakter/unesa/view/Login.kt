package com.izanacode.karakter.unesa.view

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.izanacode.karakter.unesa.databinding.ActivityLoginBinding
import com.izanacode.karakter.unesa.utils.progressDialog
import com.izanacode.karakter.unesa.viewmodel.userViewModel
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var vm: userViewModel
    lateinit var dialog : Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(userViewModel::class.java)
        dialog = progressDialog(this@Login)

        binding.btLogin.setOnClickListener {
            if (binding.etUsername.text.toString().isNullOrEmpty()){
                binding.etUsername.requestFocus()
                binding.etUsername.error = "Kosong"
            }else if (binding.etPassword.text.toString().isNullOrEmpty()){
                binding.etPassword.requestFocus()
                binding.etPassword.error = "Kosong"
            }else{
                dialog.show()
                lifecycleScope.launch {
                    try {
                        val authResponse = vm.loginuser(
                            binding.etUsername.text.toString(),
                            binding.etPassword.text.toString()
                        )
                        if (authResponse.body()!!.success == 1) {
                            dialog.dismiss()
                            vm.setLogin(this@Login, this@Login, authResponse.body()!!.data)
                        } else {
                            dialog.dismiss()
                            Toast.makeText(
                                this@Login,
                                authResponse.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (throwable: Throwable) {
                        dialog.dismiss()
                        Log.e("ERROR",throwable.toString())
                    }
                }
            }
        }
        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this, Daftar::class.java)
            startActivity(intent)
        }
          }
}