package com.freelab.tech.shootdown

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import com.freelab.tech.shootdown.utils.SystemUtils

class ShootDownApp: Application() {

    companion object {
        lateinit var INSTANCE: ShootDownApp
    }

    private var systemUtils: SystemUtils? = null

    fun getSystemUtils(): SystemUtils {
        return systemUtils ?: throw IllegalStateException("System utils is not initialized")
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
        systemUtils = SystemUtils.getInstance(this)

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    activity.window.decorView.windowInsetsController?.apply {
                        hide(WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars())
                        systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                } else {
                    @Suppress("DEPRECATION")
                    activity.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
                }
            }

            override fun onActivityStarted(p0: Activity) {
                
            }

            override fun onActivityResumed(p0: Activity) {
                
            }

            override fun onActivityPaused(p0: Activity) {
                
            }

            override fun onActivityStopped(p0: Activity) {
                
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                
            }

            override fun onActivityDestroyed(p0: Activity) {
                
            }
        })
    }

}