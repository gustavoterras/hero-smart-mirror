package br.com.terras.hero

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import br.com.terras.hero.assistant.BoardDefaults
import br.com.terras.hero.assistant.EmbeddedAssistant
import br.com.terras.hero.databinding.ActivityMainBinding
import br.com.terras.hero.viewmodel.MainViewModel
import com.google.assistant.embedded.v1alpha2.SpeechRecognitionResult
import com.google.auth.oauth2.UserCredentials
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * Hero Smart Mirror!
 *
 * Created by Gustavo Terras on 27/12/2018.
 * Copyright © 2018. All rights reserved.
 */

class MainActivity : Activity() {

    private val TAG = MainActivity::class.java.simpleName

    // Default on using the Voice Hat on Raspberry Pi 3.
    private val USE_VOICEHAT_I2S_DAC = Build.DEVICE == BoardDefaults.DEVICE_RPI3

    // Assistant SDK constants.
    private val DEVICE_MODEL_ID = "PLACEHOLDER"
    private val DEVICE_INSTANCE_ID = "PLACEHOLDER"
    private val LANGUAGE_CODE = "pt-br"

    // Audio constants.
    private val PREF_CURRENT_VOLUME = "current_volume"
    private val SAMPLE_RATE = 16000
    private val DEFAULT_VOLUME = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.setVariable(BR.viewModel, MainViewModel(this))

        var userCredentials: UserCredentials? = null
        try {
            userCredentials = EmbeddedAssistant.generateCredentials(this, R.raw.credentials)
        } catch (e: IOException) {
            Log.e(TAG, "error getting user credentials", e)
        } catch (e: JSONException) {
            Log.e(TAG, "error getting user credentials", e)
        }

        // Audio routing configuration: use default routing.
        var audioInputDevice: AudioDeviceInfo? = null
        var audioOutputDevice: AudioDeviceInfo? = null
        if (USE_VOICEHAT_I2S_DAC) {
            audioInputDevice = findAudioDevice(AudioManager.GET_DEVICES_INPUTS, AudioDeviceInfo.TYPE_BUS)
            if (audioInputDevice == null) {
                Log.e(TAG, "failed to find I2S audio input device, using default")
            }
            audioOutputDevice = findAudioDevice(AudioManager.GET_DEVICES_OUTPUTS, AudioDeviceInfo.TYPE_BUS)
            if (audioOutputDevice == null) {
                Log.e(TAG, "failed to found I2S audio output device, using default")
            }
        }

        val mEmbeddedAssistant = EmbeddedAssistant.Builder()
                .setCredentials(userCredentials)
                .setDeviceInstanceId(DEVICE_INSTANCE_ID)
                .setDeviceModelId(DEVICE_MODEL_ID)
                .setLanguageCode(LANGUAGE_CODE)
                .setAudioInputDevice(audioInputDevice)
                .setAudioOutputDevice(audioOutputDevice)
                .setAudioSampleRate(SAMPLE_RATE)
                .setAudioVolume(DEFAULT_VOLUME)
                .setRequestCallback(object : EmbeddedAssistant.RequestCallback() {
                    override fun onRequestStart() {
                        Log.i(TAG, "starting assistant request, enable microphones")
//                        mButtonWidget.setText(R.string.button_listening)
                        binding.assistant.isEnabled = false
                    }

                    override fun onSpeechRecognition(results: List<SpeechRecognitionResult>) {
                        for (result in results) {
                            Log.i(
                                    TAG, "assistant request text: " + result.transcript +
                                    " stability: " + java.lang.Float.toString(result.stability)
                            )

//                            if (result.stability > .9f)
//                                mAssistantRequestsAdapter.add(result.transcript)
                        }
                    }
                })
                .setConversationCallback(object : EmbeddedAssistant.ConversationCallback() {
//                override fun onResponseStarted() {
//                    super.onResponseStarted()
//                    // When bus type is switched, the AudioManager needs to reset the stream volume
//                    if (mDac != null) {
//                        try {
//                            mDac.setSdMode(Max98357A.SD_MODE_LEFT)
//                        } catch (e: IOException) {
//                            Log.e(TAG, "error enabling DAC", e)
//                        }
//
//                    }
//                }

//                override fun onResponseFinished() {
//                    super.onResponseFinished()
//                    if (mDac != null) {
//                        try {
//                            mDac.setSdMode(Max98357A.SD_MODE_SHUTDOWN)
//                        } catch (e: IOException) {
//                            Log.e(TAG, "error disabling DAC", e)
//                        }
//
//                    }
//                    if (mLed != null) {
//                        try {
//                            mLed.setValue(false)
//                        } catch (e: IOException) {
//                            Log.e(TAG, "cannot turn off LED", e)
//                        }
//
//                    }
//                }

                    override fun onError(throwable: Throwable) {
                        Log.e(TAG, "assist error: " + throwable.message, throwable)
                    }

//                override fun onVolumeChanged(percentage: Int) {
//                    Log.i(TAG, "assistant volume changed: $percentage")
//                    // Update our shared preferences
//                    val editor = PreferenceManager
//                        .getDefaultSharedPreferences(this@AssistantActivity)
//                        .edit()
//                    editor.putInt(PREF_CURRENT_VOLUME, percentage)
//                    editor.apply()
//                }

                    override fun onConversationFinished() {
                        Log.i(TAG, "assistant conversation finished")
//                        mButtonWidget.setText(R.string.button_new_request)
                        binding.assistant.isEnabled = true
                    }

                    override fun onAssistantResponse(response: String) {
//                        if (!response.isEmpty()) {
//                            mMainHandler.post(Runnable { mAssistantRequestsAdapter.add("Google Assistant: $response") })
//                        }
                    }

//                override fun onAssistantDisplayOut(html: String) {
//                    mMainHandler.post(Runnable {
//                        // Need to convert to base64
//                        try {
//                            val data = html.toByteArray(charset("UTF-8"))
//                            val base64String = Base64.encodeToString(data, Base64.DEFAULT)
//                            mWebView.loadData(
//                                base64String, "text/html; charset=utf-8",
//                                "base64"
//                            )
//                        } catch (e: UnsupportedEncodingException) {
//                            e.printStackTrace()
//                        }
//                    })
//                }

                    override fun onDeviceAction(intentName: String, parameters: JSONObject?) {
                        if (parameters != null) {
                            Log.d(
                                    TAG, "Get device action " + intentName + " with parameters: " +
                                    parameters.toString()
                            )
                        } else {
                            Log.d(
                                    TAG, "Get device action " + intentName + " with no paramete"
                                    + "rs"
                            )
                        }
                        if (intentName == "action.devices.commands.OnOff") {
                            try {
                                val turnOn = parameters!!.getBoolean("on")
//                            mLed.setValue(turnOn)
                            } catch (e: JSONException) {
                                Log.e(TAG, "Cannot get value of command", e)
                            } catch (e: IOException) {
                                Log.e(TAG, "Cannot set value of LED", e)
                            }

                        }
                    }
                })
                .build()
        mEmbeddedAssistant.connect()

        binding.assistant.setOnClickListener {
            mEmbeddedAssistant.startConversation()
        }
    }

    private fun findAudioDevice(deviceFlag: Int, deviceType: Int): AudioDeviceInfo? {
        val manager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val adis = manager.getDevices(deviceFlag)
        for (adi in adis) {
            if (adi.type == deviceType) {
                return adi
            }
        }
        return null
    }
}
