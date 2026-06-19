package com.mayur.feature.detail.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mayur.feature.detail.databinding.ActivityDetailBinding

class   DetailActivity : ComponentActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val universityName =
            intent.getStringExtra("name")
                ?: "No Data"

        binding.tvUniversityName.text =
            universityName

        binding.btnRefresh.setOnClickListener {
            setResult(Activity.RESULT_OK,
                Intent().apply { putExtra("refresh", true)
                }
            )
            finish()
        }
    }
}