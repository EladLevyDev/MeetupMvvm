package com.elad.meetup.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION
import android.os.Build.VERSION.SDK_INT
import androidx.core.content.ContextCompat.getSystemService



class Utils @Inject constructor(private val context: Context) {

    fun isConnectedToInternet(): Boolean {
        val connectivityMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork: NetworkInfo? = null
        if (connectivityMgr != null) {
            activeNetwork = connectivityMgr.activeNetworkInfo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nc = connectivityMgr.getNetworkCapabilities(connectivityMgr.activeNetwork)
                if (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    // connected to mobile data
                } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    // connected to wifi
                }
            } else {
                if (activeNetwork!!.type == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    // connected to mobile daך2ק5ה8ט9

                }
            }
        }
        return false
    }
}