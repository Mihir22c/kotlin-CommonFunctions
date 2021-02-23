package com.mihir.kotlin.config

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import com.mihir.kotlin.R
import com.mihir.kotlin.receiver.ConnectivityReceiver
import com.nispok.snackbar.Snackbar
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

/* Created By MIHIR BAVISI 21/02/2021 */
class CommonFunctions {
    var errMessage = ""
    var tag = "CommonFunctions :"
    var toast: Toast? = null


    /* create Progress Bar */
    var pd: ProgressDialog? = null
    var slide_act_flag = true


    /* check Internet Connection available or not
    *
    *
    * @return
    */
    var emailValid = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    fun checkConnection(activity: Activity): Boolean {

        var isConnected: Boolean = ConnectivityReceiver.isConnected()
        if (!isConnected) showDialog(
            activity,
            activity.resources.getString(R.string.msg_NO_INTERNET_RESPOND)
        )
        return isConnected
    }

    fun getDisplayWith(activity: Activity): Int {
        var metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        var orientation = activity.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return metrics.heightPixels
        }
        return metrics.widthPixels
    }

    fun convertDateEvents(date: String): String {
        var result = ""
        try {
            val myFormat = "dd-MM-yyyy hh:mm a" // In which you need put here dd-MM_yyyy
            val myFormat1 = "yyyy-MM-dd HH:mm:ss" // In which you need put here dd-MM_yyyy

            val simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)
            val simpleDateFormat1 = SimpleDateFormat(myFormat1, Locale.US)
            val startDate: Date = simpleDateFormat1.parse(date)

            result = simpleDateFormat.format(startDate.time)
            return result
        } catch (e: Exception) {
        }
        return result
    }

    fun showToast(context: Context, msg: String) {
        try {
            if (toast == null || toast!!.view!!.windowVisibility != View.VISIBLE) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
//                toast.show()
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // showing the status in SnackBar
    fun showSnack(activity: Activity, errMessage: String) {
        Snackbar.with(activity).text(errMessage).textColor(Color.RED).color(Color.BLACK)
            .show(activity)
    }

    fun sendSmsWhatsapp(activity: Activity, number: String) {
        try {
            var uri = Uri.parse("smsto:$number")
            var i = Intent(Intent.ACTION_SENDTO, uri)
            i.putExtra("sms_body", activity.getString(R.string.hi))
            i.setPackage("com.whatsapp")
            activity.startActivity(i)
        } catch (e: Exception) {
            Toast.makeText(activity, "" + e.message, Toast.LENGTH_SHORT).show();
        }
    }

    fun getDeviceInfo(activity: Activity) {
        var dm = ""
        try {
            dm =
                getDeviceManufacture() + " " + getDeviceModel() + " " + getDeviceOSVersion() + " " + getDeviceOEMBuildNumber() + " " + getDeviceSerialNumber() + " " + applicationVersionName(
                    activity
                ) + " " + applicationVersion(activity) + " " + System.currentTimeMillis()
        } catch (e: Exception) {
        }
    }

    fun e(data: String): String {
        var dm = ""
        try {
            // Sending side
            val data1 = data.toByteArray(charset("UTF-8"))
            dm = Base64.encodeToString(data1, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return dm
        }
        return dm
    }

    fun d(data: String): String {
        var dm = ""
        try {
            // Sending side
            val data1 = data.toByteArray(charset("UTF-8"))
            dm = Base64.encodeToString(data1, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return dm
        }
        return dm
    }
    @Throws(IOException::class)
    private fun convertInputStreamToString(inputStream: InputStream): String? {
        val bufferedReader = BufferedReader(
            InputStreamReader(inputStream)
        )
        var line: String? = ""
        var result: String? = ""
        while (bufferedReader.readLine().also { line = it } != null) result += line
        inputStream.close()
        return result
    }


    private fun getDeviceModel(): String? {
        var dm = ""
        return try {
            dm = Build.MODEL
            dm
        } catch (e: Exception) {
            e.printStackTrace()
            dm
        }
    }

    private fun applicationVersion(context: Context): String? {
        var version = ""
        var verCode : Int
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            version = info.versionName
            version
        } catch (e: Exception) {
            e.printStackTrace()
            version
        }
    }

    private fun applicationVersionName(context: Context): String? {
        var version = ""
        var verCode : Int
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            version = info.versionName
            version
        } catch (e: Exception) {
            e.printStackTrace()
            version
        }
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceSerialNumber(): String? {
        var dm = ""
        return try {
            dm = Build.SERIAL
            dm
        } catch (e: Exception) {
            e.printStackTrace()
            dm
        }

    }

    private fun getDeviceOEMBuildNumber(): String? {
    var dm = ""
        return try {
            dm = Build.FINGERPRINT
            dm
        } catch (e: Exception) {
            e.printStackTrace()
            dm
        }
    }

    private fun getDeviceOSVersion(): String? {
        var dm = ""
        return try {
            dm = Build.VERSION.RELEASE
            dm
        } catch (e: Exception) {
            e.printStackTrace()
            dm
        }

    }

    private fun getDeviceManufacture(): String {
        var dm = ""
        return try {
            dm = Build.MANUFACTURER
            dm
        } catch (e: Exception) {
            e.printStackTrace()
            dm
        }
    }


    private fun showDialog(context: Context, msg: String) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(context.getString(R.string.app_name))
        alertDialog.setMessage(msg)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL,
            context.getString(R.string.dialog_ok),
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
        alertDialog.show()
    }
}