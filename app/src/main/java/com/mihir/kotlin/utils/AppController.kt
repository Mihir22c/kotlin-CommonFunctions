package com.mihir.kotlin.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.StrictMode
import androidx.annotation.RequiresApi
import androidx.multidex.MultiDexApplication

/* Created By Mihir Bavisi 21/02/2021 */
class AppController : MultiDexApplication() {
    val TAG = AppController::class.java.simpleName
    var mInstance : AppController? = null

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        var builder : StrictMode.VmPolicy.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            builder = StrictMode.VmPolicy.Builder()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            StrictMode.setVmPolicy(builder!!.build())
        }
    }


    @Synchronized
    fun getInstance(): AppController {
        return AppController().mInstance!!
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun isAppInBackground(context: Context) : Boolean{
        var isInBackGround = true
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH){
            val runningAppProcesses : List<ActivityManager.RunningAppProcessInfo> =activityManager.runningAppProcesses
            for (processInfo  in runningAppProcesses){
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                    for (activeProcess in processInfo.pkgList){
                        if (activeProcess.equals(context.packageName)){
                            isInBackGround = false
                        }
                    }
                }
            }
        }
        else{
            val taskInfo : List<ActivityManager.RunningTaskInfo> = activityManager.getRunningTasks(1)
            val componentInfo = taskInfo.get(0).topActivity
            if (componentInfo!!.packageName.equals(context.packageName)){
                isInBackGround = false
            }
        }
        return isInBackGround
    }

}