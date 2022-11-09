package com.example.clase15_terremotoapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TerremotoAdapter: ListAdapter<Terremoto, TerremotoAdapter.ViewHolder>(DiffCallBack) {
    lateinit var onItemClickListener: (Terremoto) -> Unit

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private var textViewMagnitude: TextView = view.findViewById(R.id.textViewMagnitude)
        private var textViewPlace: TextView = view.findViewById(R.id.textViewPlace)

        fun bind(terremoto: Terremoto) {
            textViewMagnitude.text = terremoto.magnitude.toString()
            textViewPlace.text = terremoto.place


            view.setOnClickListener {
                onItemClickListener(terremoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val terremoto = getItem(position)
        holder.bind(terremoto)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Terremoto>() {
        override fun areItemsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return oldItem == newItem
        }
    }
}