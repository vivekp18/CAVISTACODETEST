package com.example.cavistacodetest.view.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cavistacodetest.R
import com.example.cavistacodetest.adapter.ImagesAdapter
import com.example.cavistacodetest.databinding.FragmentHomeBinding
import com.example.cavistacodetest.model.Image
import com.example.cavistacodetest.utilities.recyclerview.EndlessRecyclerViewScrollListener
import com.example.cavistacodetest.view.activity.MainActivity
import com.example.cavistacodetest.viewmodel.ImagesShapesViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    private var vm:ImagesShapesViewModel?=null

    private var imageList: ArrayList<Image>?=null

    var adapter:ImagesAdapter?=null
    var handler:Handler?=null

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
        handler = Handler()

        vm= ViewModelProviders.of(this@HomeFragment).get(ImagesShapesViewModel::class.java)

        binding?.edtSearch?.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
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

        binding?.edtSearch?.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearchOperation()
                return@OnEditorActionListener true
            }
            false
        })

    }

    fun performSearchOperation(){
        vm!!.getDataFromNetwork(binding?.edtSearch?.text.toString())
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