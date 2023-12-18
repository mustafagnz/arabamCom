package com.test.arabamcom.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.arabamcom.R
import com.test.arabamcom.adapter.PostAdapter

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)


        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        mainViewModel.post.observe(this) { postModelList ->
            // Veriler güncellendiğinde RecyclerView'a set et
            val adapter = PostAdapter(postModelList)
            recyclerView.adapter = adapter
        }


    }
}

