package com.example.cavistacodetest.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cavistacodetest.R
import com.example.cavistacodetest.databinding.ActivityMainBinding
import com.example.cavistacodetest.view.fragments.HomeFragment


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        initHomeFragment(savedInstanceState)
    }

    private fun init(){
        setSupportActionBar(binding!!.toolbar.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        closeKeyboard()
        hideSoftKeyboard()

        /** for handling title on toolbar  */
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                setToolBarTitle(getString(R.string.str_home))
            } else {

                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount-1 ).name?.let {

                    if (supportFragmentManager.backStackEntryCount >0){
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
                        setToolBarTitle(it)
                    }

                }

            }

            binding!!.toolbar.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onBackPressed()
                }
            })
        }


    }

    private fun initHomeFragment(bundle: Bundle?) {
        setToolBarTitle(getString(R.string.str_home))
        val fragment = HomeFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.content_frame, fragment)
            .replace(R.id.content_frame, fragment)
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            .commitAllowingStateLoss()
    }

    private fun setToolBarTitle(title: String) {
        binding!!.toolbar.toolbarTitle.text = title
    }


    open fun launchFragment(fragment: Fragment, title: String) {
        hideSoftKeyboard()
        if (isNetworkAvailable()) {
                supportFragmentManager
                .beginTransaction()
                .add(R.id.content_frame, fragment, title)
                .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                .addToBackStack(title)
                .commitAllowingStateLoss()

        } else {
            showToast(getString(R.string.internet_connection_failed))
        }
    }


    fun showToast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard() {

        if (currentFocus != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }


    fun closeKeyboard() {
        try {
            val inputManager =
                applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                this.currentFocus?.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "closeKeyboard: $e")
        }

    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        val networkState = networkInfo.state
        return networkState == NetworkInfo.State.CONNECTED || networkState == NetworkInfo.State.CONNECTING
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("Data--->","on back pressed")
        val fm = supportFragmentManager
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        }
    }

    open fun popAllFragmentFromBackStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    open fun popSingleFragmentFromBackStack() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        }
    }


//    override fun onBackPressed() {
//        super.onBackPressed()
//
//    }

}