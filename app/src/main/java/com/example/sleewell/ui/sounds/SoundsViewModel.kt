package com.example.sleewell.ui.sounds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SoundsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is sounds Fragment"
    }
    val text: LiveData<String> = _text
}