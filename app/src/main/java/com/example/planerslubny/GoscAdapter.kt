package com.example.planerslubny

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoscAdapter {

    class GoscAdapter(private val lista: Array<String?>) :
        RecyclerView.Adapter<GoscAdapter.GoscViewHolder>() {

        class GoscViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.rowText)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoscViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item, parent, false)
            return GoscViewHolder(view)
        }

        override fun onBindViewHolder(holder: GoscViewHolder, position: Int) {
            val item = lista[position]
            for(i in lista){
                if (item != null) {
                    holder.textView.text = "Stół ${position+1}\n${item.replace(";","\n")}"
                }
            }
        }

        override fun getItemCount() = lista.size
    }


}