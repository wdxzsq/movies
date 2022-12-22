package com.example.movies.view

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.movies.R
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.view.ui.sign_up.SignUpFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val MAIN_KEY = "main_settings"
const val MAIN_IS_LAUCNH = "is_launch"

class MainActivity : AppCompatActivity() {

    companion object {
        var stateLaunch = false
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appSetting: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appSetting = getSharedPreferences(MAIN_KEY, MODE_PRIVATE)
        navController = findNavController(R.id.nav_host_fragment_content_main)

        checkInternetDialog()

    }

    private fun checkInternetDialog() {
        if (checkInternet()) {
            if (!stateLaunch) initLaunch()
        }
        else {
            MaterialAlertDialogBuilder(this)
                .setTitle("Нет подключения к интернету!")
                .setPositiveButton("Повторить") { _, _ ->
                    checkInternetDialog()
                }
                .setNegativeButton("Закрыть") { _, _ ->
                    finish()
                }
                .show()
        }
    }

    private fun initLaunch() = MainScope().launch {
        delay(2000)
        if (appSetting.getBoolean(MAIN_IS_LAUCNH, false)) {
            navController.navigate(R.id.action_launchFragment_to_FirstFragment)
        } else {
            navController.navigate(R.id.action_launchFragment_to_SecondFragment)
            appSetting.edit().putBoolean(MAIN_IS_LAUCNH, true).apply()
        }
        stateLaunch = true
    }

    private fun checkInternet(): Boolean {
        val connectManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectManager.activeNetwork ?: return false
            return  true
//            val activeNetwork = connectManager.getNetworkCapabilities(network) ?: return false
//            return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//                    || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
        return false
    }

}