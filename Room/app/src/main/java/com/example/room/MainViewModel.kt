package com.example.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (
    application: Application
) : AndroidViewModel(application) {
    private val noteDao: NoteDao =
        NoteDatabase.getDatabase(application).noteDao()
    private val _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>> get() = _allNotes

    fun queryAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _allNotes.postValue(noteDao.getAllNotes())
        }
    }

    fun insert(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insertNote(note)
            queryAllNotes()
        }
    }

    fun delete(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(note)
            queryAllNotes()
        }
    }

    fun update(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.updateNote(note)
            queryAllNotes()
        }
    }

    init {
        _allNotes.value = emptyList()
    }
}