package com.example.mobiledevelopment.src.domain

import java.beans.PropertyChangeListener

interface Observable {
    fun addObserver(observer: PropertyChangeListener)
}