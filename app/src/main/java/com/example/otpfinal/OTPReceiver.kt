package com.example.otpfinal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
class OTPReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras: Bundle? = intent!!.extras
            val status: Status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    // Get SMS message contents
                    var msg: String? = extras?.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String

                    // extract the 6-digit code from the SMS
                    val smsCode = msg?.let { "[0-9]{6}".toRegex().find(it) }

                    smsCode?.value?.let {
                        onReceiveOtpListener.invoke(it)
                    }
                }

                CommonStatusCodes.TIMEOUT -> {
                    onTimeoutListener.invoke("timeout")
                }
            }
        }
    }

    companion object {
        var onReceiveOtpListener: ((String) -> Unit) = {}
        var onTimeoutListener: ((String) -> Unit) = {}
    }

}

fun startSMSRetrieverClient(context: Context) {
    val client: SmsRetrieverClient = SmsRetriever.getClient(context)
    val task = client.startSmsRetriever()
    task.addOnSuccessListener { aVoid ->

    }
    task.addOnFailureListener { e ->

    }
}