package com.example.room

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.example.room.databinding.DialogAddNoteBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doInitialize()
        setupNoteList()
        setListener()
    }

    private fun doInitialize(){
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel  = ViewModelProvider(this)[MainViewModel::class.java]
        binding.lifecycleOwner = this
        binding.vm = viewModel
        viewModel.queryAllNotes()
    }

    private fun setupNoteList(){
        noteAdapter = NoteAdapter(
            onNoteClick = {note -> showNoteEditDialog(note) },
            onNoteLongClick = {note: Note ->  showNoteDeleteDialog(note)}
        )

        val layoutManager = LinearLayoutManager(this)
        binding.rvNotes.layoutManager= layoutManager
        binding.rvNotes.adapter = noteAdapter
        viewModel.allNotes.observe(this){ notes ->
            noteAdapter.setNotes(notes)
        }

    }

    private fun setListener(){
        binding.fabAdd.setOnClickListener(){
            showNoteEditDialog(null)
        }
    }

    private fun showNoteEditDialog(note: Note?){
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)

        if (note!= null){
            dialogBinding.edTitle.setText(note.title)
            dialogBinding.edContent.setText(note.content)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle(
                if (note == null) "新增記事本" else  "編輯記事本"
            )
            .setView(dialogBinding.root)
            .setPositiveButton("保存"){_,_ ->
                val title = dialogBinding.edTitle.text.toString()
                val content = dialogBinding.edContent.text.toString()
                val newNote = Note(
                    id = note?.id ?: 0,
                    title= title,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )

                if(note!= null){
                    viewModel.update(newNote)
                }else{
                    viewModel.insert(newNote)
                }
            }
            .setNegativeButton("取消",null)
            .create()
        dialog.show()
    }

    private fun showNoteDeleteDialog(note: Note){
        val dialog = AlertDialog.Builder(this)
            .setTitle("刪除筆記本")
            .setMessage("確定要刪除這篇筆記本嗎？")
            .setPositiveButton("確定"){_,_ ->
                viewModel.delete(note)
            }
            .setNegativeButton("取消",null)
            .create()
        dialog.show()
    }
}