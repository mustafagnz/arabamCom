package com.test.arabamcom.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.test.arabamcom.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            if (isNetworkAvailable()) {
                Log.d("NetworkCheck", "net var")
                showSplashScreenWithDelay()
            } else {
                Log.d("NetworkCheck", "net yok")
                showNoInternetDialog()
            }
        }
    }


    private suspend fun isNetworkAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                try {
                    // Bir URL e istek atıyoruz
                    val command = "ping -c 1 google.com"

                    // Eğer ping başarılı olursa internet bağlantısı olduğu kabul edilir
                    Runtime.getRuntime().exec(command).waitFor() == 0
                } catch (e: Exception) {
                    // Hata durumunda istisna yakalanır
                    e.printStackTrace()
                    false
                }
            } else {
                // Eğer cihazın ağ durumu doğru değilse veya ping başarısız olursa, false döndürülür
                false
            }
        }
    }


    private fun showSplashScreenWithDelay() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(this)
            .setTitle("İnternet Bağlantısı Yok")
            .setMessage("Lütfen internet bağlantınızı kontrol edin ve tekrar deneyin.")
            .setPositiveButton("Çıkış") { _: DialogInterface?, _: Int ->
                finish()
            }
            .setNegativeButton("Tekrar Dene") { _: DialogInterface?, _: Int ->
                CoroutineScope(Dispatchers.Main).launch{
                    if (isNetworkAvailable()) {
                        showSplashScreenWithDelay()
                    } else {
                        showNoInternetDialog()
                    }
                }

            }
            .setCancelable(false)
            .show()
    }

}
