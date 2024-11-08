package com.example.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.adapters.SearchViewBindingAdapter.OnSuggestionClick
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemNoteBinding
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class NoteAdapter (
    private val onNoteClick:(Note) -> Unit,
    private val onNoteLongClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>()  {
    private var notes: List<Note> = emptyList()

    fun setNotes(newNote: List<Note>){
        this.notes = newNote
        notifyDataSetChanged()
    }

    class NoteViewHolder(
        private val binding: ItemNoteBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(
            note: Note,
            onNoteClick: (Note) -> Unit,
            onNoteLongClick: (Note) -> Unit
        ){
            binding.note = note
            binding.formatTime = toFormattedDateString(note.timestamp)
            binding.root.setOnClickListener{onNoteClick(note)}
            binding.root.setOnLongClickListener{
                onNoteLongClick(note)
                true
            }
            binding.executePendingBindings()
        }


        private fun toFormattedDateString(time: Long):String{
            val dateFormat = SimpleDateFormat(
                "yyyy/MM/dd HH:mm", Locale.getDefault()
            )
            return dateFormat.format(Date(time))
        }

        companion object {
            fun from(parent: ViewGroup) = NoteViewHolder(
                ItemNoteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,false
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position],onNoteClick,onNoteLongClick)
    }

}