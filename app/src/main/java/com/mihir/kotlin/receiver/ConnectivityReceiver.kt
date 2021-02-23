package com.mihir.kotlin.receiver
/* created by Mihir Bavisi 21/02/2021 */
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.mihir.kotlin.utils.AppController

class ConnectivityReceiver : BroadcastReceiver() {

    var connectivityReceiverListener: ConnectivityReceiverListener? = null


    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected)
        }
    }


   companion object{
       fun isConnected(): Boolean {
           val cm = AppController().getInstance().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
           return activeNetwork != null && activeNetwork.isConnectedOrConnecting
       }
   }


    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean) {}
    }
}