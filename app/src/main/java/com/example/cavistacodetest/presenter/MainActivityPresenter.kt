package com.example.cavistacodetest.presenter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.TextView

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.content.ContentValues.TAG
import com.example.cavistacodetest.view.activity.MainActivity
import com.example.cavistacodetest.R
import com.example.cavistacodetest.callbacks.OkCancelCallback
import com.example.cavistacodetest.contants.ApiConstants
import com.example.cavistacodetest.contract.MainContract
import com.example.cavistacodetest.network.ApiClient
import com.example.cavistacodetest.network.ApiInterface

class MainActivityPresenter(private val mView: MainContract.View) : MainContract.Presenter {
    private val model: MainContract.Model? = null
    private var progressDialog: ProgressDialog? = null

    init {
        initPresenter()
    }

    private fun initPresenter() {
        mView.initView() // calling activity init method.. now user can design its view
    }

    override fun onClick(
        caseConstants: ApiConstants,
        para: Array<String>,
        context: Context,
        showProgressBar: Boolean?
    ) {
        if (showProgressBar!!) {
            initAndShowProgressBar(context)
        }

        val retrofit = ApiClient.client
        val requestInterface = retrofit.create(ApiInterface::class.java)
        val accessTokenCall: Call<JsonObject>
        when (caseConstants) {

            ApiConstants.GET_IMAGES -> {
                accessTokenCall = requestInterface.getImages(para[0],para[1])
                callApiWithAccessToken(accessTokenCall, context, ApiConstants.GET_IMAGES)
            }

        }
    }

    private fun initAndShowProgressBar(context: Context) {
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage(context.getString(R.string.str_please_wait))
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCancelable(false)
        try {
            progressDialog!!.show()
        } catch (e: Exception) {
            Log.e("TAG", "initAndShowProgressBar: $e")
        }
    }

    override fun onClick(
        caseConstants: ApiConstants,
        para: Array<String>,
        files: Array<Uri>,
        context: Context,
        showProgressBar: Boolean?
    ) {
        if (showProgressBar!!) {
            initAndShowProgressBar(context)
        }

        val retrofit = ApiClient.client
        val requestInterface = retrofit.create(ApiInterface::class.java)
        val accessTokenCall: Call<JsonObject>


        when (caseConstants) {

            /*---------------EOF Ritesh------------------------*/
        }

    }


    private fun callApiWithAccessToken(
        accessTokenCall: Call<JsonObject>,
        context: Context,
        view: ApiConstants
    ) {
        try {
            if (isNetworkAvailable(context)) {
                accessTokenCall.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (progressDialog != null && progressDialog!!.isShowing)
                            try {
                                if (progressDialog!!.isShowing)
                                    progressDialog!!.dismiss()
                            } catch (e: Exception) {
                                Log.e(TAG, "onResponse: $e")
                            }

                        if (response.body() != null) {
                            if (response.body()!!.get("status_code") != null && response.body()!!.get(
                                    "status_code"
                                ).asInt == 403
                            ) {
                                okCancelDialog(
                                    context,
                                    response.body()!!.get("message").asString,
                                    "Ok",
                                    "",
                                    object : OkCancelCallback {
                                        override fun onOkClick() {

                                            val intent = Intent(context, MainActivity::class.java)
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            context.startActivity(intent)
                                        }

                                        override fun onCancelClick() {

                                        }
                                    })
                            } else {
                                //val data = model!!.getData)
                                mView.setViewData((response.body()!!.toString()), view)
                            }

                        }
                        else if (response.code() == 500) {
                            okCancelDialog(
                                context,
                                "500 - Server is under maintenance right now. Please try after some time.",
                                "Ok",
                                "",
                                object : OkCancelCallback {
                                    override fun onOkClick() {

                                    }

                                    override fun onCancelClick() {
                                    }
                                })
                        }else {
                            if (isNetworkAvailable(context)) {

                                  } else {
                                    }
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        try {

                            if (progressDialog != null && progressDialog!!.isShowing)
                                progressDialog!!.dismiss()
                        } catch (e: Exception) {
                            Log.e(TAG, "onFailure: $e")
                        }

                        if (isNetworkAvailable(context)) {
                        } else {
                        }
                        t.printStackTrace()
                    }
                })
            } else {
                if (progressDialog!!.isShowing)
                    try {

                        progressDialog!!.dismiss()
                    } catch (e: Exception) {
                        Log.e(TAG, "onResponse: $e")

                    }

                if (isNetworkAvailable(context)) {

                 } else {
                }
            }
        } catch (e: Exception) {
            try {

                if (progressDialog!!.isShowing)
                    progressDialog!!.dismiss()
            } catch (e1: Exception) {
                Log.e(TAG, "callApiWithAccessToken: $e1")
            }

            if (isNetworkAvailable(context)) {
           } else {
            }
            Log.i(TAG, "------------------>$e")
            e.printStackTrace()
        }

    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE
            )?.state == NetworkInfo.State.CONNECTING ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            )?.state == NetworkInfo.State.CONNECTING
        ) {
            //we are connected to a network
            true
        } else false
    }

    fun okCancelDialog(
        context: Context,
        message: String,
        okText: String,
        cancelText: String,
        okCancel: OkCancelCallback
    ) {
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        val view = View.inflate(context, R.layout.dialog_ok_cancel_network, null)
        alertDialog.setView(view)
        alertDialog.setCancelable(false)
        val msg = view.findViewById<TextView>(R.id.msg)
        msg.text = message
        val sureBtn = view.findViewById<TextView>(R.id.sureBtn)
        sureBtn.text = okText
        val cancelBtn = view.findViewById<TextView>(R.id.cancelBtn)
        cancelBtn.text = cancelText
        val title = view.findViewById<TextView>(R.id.title)
        title.visibility = View.GONE

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sureBtn.setOnClickListener { v ->
            okCancel.onOkClick()
            //            commonDialogs.okCommonDialog("Your order has been successfully placed. Please pickup your order within " + binding.defaultMinText.getText() + " mins.");
            alertDialog.dismiss()
        }
        cancelBtn.setOnClickListener { v ->
            okCancel.onCancelClick()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}