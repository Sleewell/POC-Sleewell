package com.example.sleewell.ui.alarms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is alarm Fragment"
    }
    val text: LiveData<String> = _text
}