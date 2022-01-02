package com.example.mvvmdemo.taobao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdemo.R

class RecommendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        val viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application)).get(RecommendViewModel::class.java)

    }
}