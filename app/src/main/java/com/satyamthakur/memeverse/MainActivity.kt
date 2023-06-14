package com.satyamthakur.memeverse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satyamthakur.memeverse.api.RetrofitClient
import com.satyamthakur.memeverse.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var memeUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setting status bar icons to dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        val navController = this.findNavController(R.id.nav_host_fragment)

        // Find reference to bottom navigation view
        val navView: BottomNavigationView = binding.bottomNavView
        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)

    }
//
//    fun sendText(message: String) {
//        val sendIntent = Intent(Intent.ACTION_SEND)
//        sendIntent.type = "text/plain"
//        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
//        startActivity(Intent.createChooser(sendIntent, "Send via"))
//    }


//    private fun getMemeNow() {
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val response = RetrofitClient.apiService.getMemes()
//                if (response.isSuccessful) {
//                    val user = response.body()
//                    Log.d("MEMEVERSE", user.toString())
//                    memeUrl = user!!.preview.get(user!!.preview.size - 1)
//                    loadImage(memeUrl)
//
//                } else {
//                    Log.e("RETROFIT_ERROR", response.code().toString())
//                }
//            } catch (e: Exception) {
//                Log.e("RETROFIT_ERROR", e.message.toString())
//            }
//        }
//    }
}