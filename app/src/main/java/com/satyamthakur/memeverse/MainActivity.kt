package com.satyamthakur.memeverse

import android.Manifest
import android.annotation.TargetApi
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.satyamthakur.memeverse.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var memeUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMemeNow()

        binding.nextMeme.setOnClickListener {
            getMemeNow()
        }

        binding.saveMemeBtn.setOnClickListener {
            // Implement share meme instead
            sendText(memeUrl)
        }
    }

    fun sendText(message: String) {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(sendIntent, "Send via"))
    }


//    private fun requestPermissions() {
//        var permissionToRequest = mutableListOf<String>()
//        if (!hasWriteExternalStoragePermission()) {
//            permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }
//
//        if (permissionToRequest.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionToRequest.toTypedArray(), 0)
//        }
//    }

//    private fun hasWriteExternalStoragePermission() =
//        ActivityCompat.checkSelfPermission(this,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun loadImage(url: String) {
        binding.memeImageView.load(url) {
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.ic_downloading)
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 0 && grantResults.isNotEmpty()) {
//            for (i in grantResults.indices) {
//                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("PermissionRequest", "${permissions[i]} granted.")
//                }
//            }
//        }
//    }

    private fun getMemeNow() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getMemes()
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("MEMEVERSE", user.toString())
                    memeUrl = user!!.preview.get(user!!.preview.size - 1)
                    loadImage(memeUrl)

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("RETROFIT_ERROR", e.message.toString())
            }
        }
    }
}