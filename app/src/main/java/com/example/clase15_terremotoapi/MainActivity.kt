package com.example.clase15_terremotoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), FeatureSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onFeatureSelected(terremoto: Terremoto) {
        val bundle = Bundle()
        bundle.putParcelable("terremoto", terremoto)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, detailFragment)
            .commit()
    }
}
