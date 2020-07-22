package com.example.cavistacodetest.contract

import android.content.Context
import android.net.Uri
import com.example.cavistacodetest.contants.ApiConstants

interface MainContract {

    interface View {
        fun initView()

        fun setViewData(data: String, view: ApiConstants)
    }

    // for getting data from Api as a pojo class.
    interface Model {
        fun getData(jsonObject: String): String
        //  String setData(String s);
    }

    // for network calling..
    interface Presenter {
        //

        fun onClick(caseConstants: ApiConstants, parameters: Array<String>, context: Context, showProgressBar: Boolean?)
        fun onClick(
            caseConstants: ApiConstants,
            s: Array<String>,
            files: Array<Uri>,
            context: Context,
            showProgressBar: Boolean?
        )
    }

}