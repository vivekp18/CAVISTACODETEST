package com.example.cavistacodetest.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.cavistacodetest.contants.ApiConstants
import com.example.cavistacodetest.contract.MainContract
import com.example.cavistacodetest.database.AppDatabase
import com.example.cavistacodetest.database.Dao.ImageCommentsDao
import com.example.cavistacodetest.model.Image
import com.example.cavistacodetest.model.ImageShapes
import com.example.cavistacodetest.presenter.MainActivityPresenter
import com.google.gson.Gson

class ImageShapesRepository constructor(val application: Application) : MainContract.View {

    override fun initView() {
    }

    var imagesShapeDao: ImageCommentsDao
    var listLiveData: MutableLiveData<List<Image>>
     var presenter: MainActivityPresenter? = null

    init {

        val database = AppDatabase.getDatabase(application)

        imagesShapeDao = database!!.imageShapeDao()

        listLiveData = MutableLiveData<List<Image>>()

        presenter = MainActivityPresenter(this)
    }


    fun getDataFromNetwork(searchText :String) {
        presenter!!.onClick(
            ApiConstants.GET_IMAGES,
            arrayOf("Client-ID 137cda6b5008a7c", searchText),
            application.applicationContext,
            true
        )
    }

    override fun setViewData(data: String, view: ApiConstants) {
        when (view) {
            ApiConstants.GET_IMAGES -> {
                val response = Gson().fromJson(data, ImageShapes::class.java)
                //Log.d("Data from server :---> " , response.data[0].images.toString())
                if(response.success){
                    val list = mutableListOf<Image>()
                    response.data.forEach {parent ->
                        if(parent.images!=null){
                            list.addAll(parent.images.map { child -> Image(child.id,parent.title,child.link) })
                        }

                    }
                    listLiveData.postValue(list)
                }
            }
        }
    }


}