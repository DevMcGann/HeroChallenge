package com.intermedia.challenge.ui.comics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intermedia.challenge.data.models.Appearance
import com.intermedia.challenge.data.models.Comic
import com.intermedia.challenge.data.models.NetResult
import com.intermedia.challenge.data.repositories.ComicsRepository
import kotlinx.coroutines.launch

class ComicsViewModel (private val comicsRepository: ComicsRepository) : ViewModel() {
    private val _comicEvents = MutableLiveData<List<Comic>>()
    val comicEvents: LiveData<List<Comic>> get() = _comicEvents
    private var errors : Boolean = false
    private var newOffset = 0

    init {
        loadEvents(0)
    }

    fun errorExists() : Boolean {
        return errors
    }


    private fun loadEvents(offset: Int) {
        viewModelScope.launch {
            when (val response = comicsRepository.getComics(offset)) {
                is NetResult.Success -> {
                    errors = false
                    newOffset += 25
                    _comicEvents.postValue(response.data.comicList.comics)
                }
                is NetResult.Error -> {
                    errors = true
                }
            }
        }
    }

    fun loadMoreEvents() {
        Log.d("ViewModel", "LoadMoreEvents!")
        loadEvents(newOffset)
    }
}