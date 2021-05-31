package com.example.scopedmediaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scopedmediaexample.databinding.ActivityMainBinding
import com.example.scopedmediaexample.databinding.ActivityShowAllBinding

class show_all : AppCompatActivity() {
    lateinit var binding : ActivityShowAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowAllBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//
//        binding.allimagebutton.setOnClickListener{
//
//        }
    }
}