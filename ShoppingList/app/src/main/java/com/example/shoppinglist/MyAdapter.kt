package com.example.shoppinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(
    private val data: List<Item>,
    private val layout: Int
) :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(
        v: View
    ): RecyclerView.ViewHolder(v){
        private val imgPhoto: ImageView = v.findViewById(R.id.imgPhoto)
        private val tvMsg: TextView = v.findViewById(R.id.tvMsg)

        fun bind(item: Item){
            imgPhoto.setImageResource(item.photo)
            tvMsg.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layout,parent, false)
            return MyViewHolder(view)
        }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
