package com.example.cavistacodetest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cavistacodetest.model.Image
import com.example.cavistacodetest.model.ImageShapes
import com.example.cavistacodetest.repository.ImageShapesRepository

class ImagesShapesViewModel constructor (application: Application) : AndroidViewModel(application) {

    val mRepository = ImageShapesRepository(application)
    var allImageShapes: LiveData<List<Image>>?=null

    init {
        allImageShapes = mRepository.listLiveData!!
    }

    internal fun getDataFromNetwork(searchText: String) {
        mRepository.getDataFromNetwork(searchText)
    }

}