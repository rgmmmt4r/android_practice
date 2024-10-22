package com.example.shoppinglist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class MyAdapter(
    context: Context,
    data: List<Item>,
    private val layout: Int) :ArrayAdapter<Item>(context,layout,data) {
    class ViewHolder(v: View) {
        val imgPhoto: ImageView = v.findViewById(R.id.imgPhoto)
        val tvMsg: TextView = v.findViewById(R.id.tvMsg)
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View
        val holder: ViewHolder
        if(convertView == null){
            view = View.inflate(context,layout,null)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = view.tag as ViewHolder
        }
        val item = getItem(position) ?: return view
        holder.imgPhoto.setImageResource(item.photo)
        holder.tvMsg.text = if(layout == R.layout.adapter_vertical){
            item.name

        }else{
            "${item.name}: ${item.price} 元"
        }
        return view
    }
    }