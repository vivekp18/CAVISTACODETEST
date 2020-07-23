package com.example.cavistacodetest.view.fragments

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cavistacodetest.R
import com.example.cavistacodetest.adapter.ImagesAdapter
import com.example.cavistacodetest.contants.CLIENT_ID
import com.example.cavistacodetest.databinding.FragmentHomeBinding
import com.example.cavistacodetest.model.Image
import com.example.cavistacodetest.view.activity.MainActivity
import com.example.cavistacodetest.viewmodel.ImagesShapesViewModel
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    private var vm:ImagesShapesViewModel?=null

    private var imageList: ArrayList<Image>?=null

    private val DELAY: Long = 250 // milliseconds

    private var searchTask : Runnable ?=null
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        init()
        return binding?.root

    }

    private fun init() {
        imageList = ArrayList()

        vm= ViewModelProviders.of(this@HomeFragment).get(ImagesShapesViewModel::class.java)

        /**
         * Drawable Right search icon click listner
         * */

        binding?.edtSearch?.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding?.edtSearch?.right!! - binding?.edtSearch?.compoundDrawables?.get(DRAWABLE_RIGHT)?.bounds?.width()!!
                ) {
                    performSearchOperation()
                    (context as MainActivity).closeKeyboard()
                    return@OnTouchListener true
                }
            }
            false
        })


        /**
        * For debounce of 250 MS
        * */
        binding?.edtSearch?.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    mHandler.removeCallbacks(searchTask!!);
                    mHandler.postDelayed(searchTask!!, DELAY)
                }
            })

            searchTask = Runnable {
                performSearchOperation()
            }

    }





    fun performSearchOperation(){

        vm!!.getDataFromNetwork( CLIENT_ID,binding?.edtSearch?.text.toString())

        vm!!.allImageShapes?.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding!!.rvImageShapes.visibility = View.VISIBLE
                binding!!.rvImageShapes.setHasFixedSize(true);
                binding!!.rvImageShapes.addItemDecoration( DividerItemDecoration(context,
                    DividerItemDecoration.HORIZONTAL))
                binding!!.rvImageShapes.addItemDecoration( DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL))
                binding!!.rvImageShapes.layoutManager = GridLayoutManager(context,4)
                binding!!.rvImageShapes.adapter = ImagesAdapter(it  ,binding!!.rvImageShapes){
                    var fragment = ImageShapeDetailsFragment()
                    var bundle = Bundle()
                    bundle.putString("image_url", it.link)
                    bundle.putString("image_id", it.id)
                    fragment.arguments = bundle
                    it.title?.let { title ->
                        (context as MainActivity).launchFragment(
                            fragment,
                            "Image Details"
                        )
                    }
                }

            } else {
                binding!!.rvImageShapes.visibility = View.GONE
            }

        })
    }



}