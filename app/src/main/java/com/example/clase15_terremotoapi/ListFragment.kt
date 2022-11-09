package com.example.clase15_terremotoapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ClassCastException

class ListFragment : Fragment() {
    private var listadoTerremotos = mutableListOf<Terremoto>()
    private val job = Job()
    private lateinit var adapter: TerremotoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: FeatureSelectedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = myView.findViewById(R.id.listrecyclerview)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = TerremotoAdapter()
        recyclerView.adapter = adapter
        getTerremotos()

        adapter.onItemClickListener = { terremoto ->
            Toast.makeText(activity, terremoto.place, Toast.LENGTH_SHORT).show()
            listener.onFeatureSelected(terremoto)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = try {
            context as FeatureSelectedListener
        } catch (e: ClassCastException) {
            throw  ClassCastException("$context debe implementar el listener")
        }
    }

    private fun getTerremotos() {
        CoroutineScope(Dispatchers.IO + job).launch {
            val call = getRefrofit().create(ApiService::class.java).getAllWeek()
            val response = call.body()

            activity?.runOnUiThread {
                listadoTerremotos.clear()
                if (call.isSuccessful) {
                    listadoTerremotos = (response?.features?.let {
                        parseFeatureToTerremoto(it)
                    } ?: emptyList()) as MutableList<Terremoto>
                    adapter.submitList(listadoTerremotos)

                } else {
                    val error = call.errorBody().toString()
                    Log.i("RETROFIT", error)
                }
            }
        }
    }

    fun parseFeatureToTerremoto(features: MutableList<Feature>): MutableList<Terremoto> {
        val lista = mutableListOf<Terremoto>()

        for (feature in features ) {
            val id = feature.id
            val magnitud = feature.properties.mag
            val place = feature.properties.place
            val duracion = feature.properties.time

            val longitud = feature.geometry.longitude
            val latitud = feature.geometry.latitude

            val terremoto = Terremoto(id, magnitud, place, duracion, longitud, latitud)
            lista.add(terremoto)
        }
        return lista
    }

    private fun getRefrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}