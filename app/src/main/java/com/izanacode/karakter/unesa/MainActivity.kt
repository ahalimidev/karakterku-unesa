package com.izanacode.karakter.unesa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.izanacode.karakter.unesa.databinding.ActivityMainBinding
import com.izanacode.karakter.unesa.viewmodel.userViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var vm: userViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(userViewModel::class.java)
        vm.getLogin(this,this)

    }
}