package com.example.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.databinding.databinding.ItemKeyboardBinding

class KeyboardAdapter (
    private val keys: List<String>,
    private val onKeyClick: (String) -> Unit
): RecyclerView.Adapter<KeyboardAdapter.KeyboardViewHolder>(){

    class KeyboardViewHolder(
        private val binding: ItemKeyboardBinding
    ): ViewHolder(binding.root){
        fun bind(
            item: String,
            onKeyClick: (String) -> Unit
        ){
            binding.key = item
            binding.isNumber = item.toIntOrNull() != null
            binding.root.setOnClickListener(){onKeyClick(item)}
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent:ViewGroup) = KeyboardViewHolder(
                ItemKeyboardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,false
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardViewHolder {
        return KeyboardViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return keys.size
    }

    override fun onBindViewHolder(holder: KeyboardViewHolder, position: Int) {
        holder.bind(keys[position],onKeyClick)
    }

}