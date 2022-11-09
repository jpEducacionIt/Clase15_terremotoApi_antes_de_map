package com.example.clase15_terremotoapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.clase15_terremotoapi.R.*

class DetailFragment : Fragment() {
    private lateinit var terremoto: Terremoto
    private lateinit var textViewName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            terremoto = it.get("terremoto") as Terremoto
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(layout.fragment_detail, container, false)
        textViewName = myView.findViewById(R.id.textViewNombre)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewName.text = terremoto.place
    }
}