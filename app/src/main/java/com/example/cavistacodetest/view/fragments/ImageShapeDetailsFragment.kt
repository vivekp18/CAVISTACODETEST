package com.example.cavistacodetest.view.fragments

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.cavistacodetest.R
import com.example.cavistacodetest.database.AppDatabase
import com.example.cavistacodetest.database.Dao.ImageCommentsDao
import com.example.cavistacodetest.databinding.FragmentImageShapeDetailsBinding
import com.example.cavistacodetest.model.ImageComments
import com.example.cavistacodetest.utilities.AppDetails.activity
import com.example.cavistacodetest.view.activity.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_shape_details.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.function.Predicate

class ImageShapeDetailsFragment : Fragment() {
    private var binding: FragmentImageShapeDetailsBinding? = null
    private var image_url :String?=null
    private var image_id :String?=null
    var database :AppDatabase?=null
    var imagesShapeDao: ImageCommentsDao?=null
    var imageComments :ImageComments?=null
    var listOfAllEntries : LiveData<List<ImageComments>>?=null
    var mExecutor:Executor?=null
    var imageListItems : ArrayList<ImageComments>?=null
    var imageCommentsData :LiveData<ImageComments>?=null
    var insertedId : Long?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_shape_details, container, false)
        init()
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun init(){
        database= AppDatabase.getDatabase(context!!)
        imagesShapeDao = database!!.imageShapeDao()
        mExecutor = Executors.newSingleThreadExecutor()
        imageListItems =  arrayListOf()

       if (arguments != null) {
             image_url = arguments!!.getString("image_url")
            image_id = arguments!!.getString("image_id")
            imageComments?.imageId = image_id
        }


        listOfAllEntries = database!!.imageShapeDao().getAllImageShapesList

        imageCommentsData =  database!!.imageShapeDao().getSingleImageComment(image_id.toString())

        imageCommentsData?.observe(
            this, Observer {
                if( it != null){
                    binding!!.edtComments.setText(it.comments)
                }
            })

        listOfAllEntries?.observe(
            this, Observer {it.forEach {
                imageListItems?.add(it)
            }
            })

        Picasso.with(context)
            .load(image_url)
            .fit()
            .error(R.drawable.placeholder)
            .into(binding?.ivShapes)


        binding?.btnSubmitComments?.setOnClickListener {

            if(isCommentsValid()){
                var id :Long?=null
                if(containsName(imageListItems!!,image_id)){
                    updateAsyncTask(imagesShapeDao!!).execute(ImageComments(image_id,edtComments.text.toString()))
                }else{
                    insertAsyncTask(imagesShapeDao!!).execute(ImageComments(image_id,edtComments.text.toString()))

                }

                listOfAllEntries?.observe(this, Observer {it.forEach { imageListItems?.add(it) } })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun containsName(list: List<ImageComments>, imageId: String?): Boolean {
        return list.stream().filter(Predicate<ImageComments> { o: ImageComments -> o.imageId.equals(imageId) }).findFirst().isPresent()
    }

    fun isCommentsValid():Boolean{

        if(binding!!.edtComments.text.isNullOrBlank()){
            Toast.makeText(activity ,"Please enter comments for images",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private class insertAsyncTask internal constructor(dao: ImageCommentsDao) :
        AsyncTask<ImageComments?, Void?, Void?>() {
        private val mAsyncTaskDao: ImageCommentsDao
        override fun doInBackground(vararg params: ImageComments?): Void? {

            params[0]?.let { mAsyncTaskDao.insert(it) }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(activity,"Comments has been inserted successfully ",Toast.LENGTH_SHORT).show()
        }

        init {
            mAsyncTaskDao = dao
        }
    }

    private class updateAsyncTask internal constructor(dao: ImageCommentsDao) :
        AsyncTask<ImageComments?, Void?, Void?>() {
        private val mAsyncTaskDao: ImageCommentsDao
        override fun doInBackground(vararg params: ImageComments?): Void? {
            //params[0]?.let { mAsyncTaskDao.insert(it) }
            val imageId: String = params[0]?.imageId.toString()
            val comments: String = params[0]?.comments.toString()
            mAsyncTaskDao.update(comments,imageId)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

        }

        init {
            mAsyncTaskDao = dao
        }
    }




}