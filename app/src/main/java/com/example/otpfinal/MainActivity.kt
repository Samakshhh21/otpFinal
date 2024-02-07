package com.example.otpfinal

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.otpfinal.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val context = this
        val appSignatureHelper = AppSignatureHelper(context)
        Log.e("heyboy", "key: ${appSignatureHelper.appSignatures}")
        startSMSRetrieverClient(context)


        OTPReceiver.onReceiveOtpListener = {
           binding.editTextOtp.setText(it)
        }
        OTPReceiver.onTimeoutListener = {

        }

    }

}
