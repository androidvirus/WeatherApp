package com.synchronoss.harshesh.assignment.view

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.synchronoss.harshesh.assignment.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.synchronoss.harshesh.assignment.`interface`.Constant
import com.synchronoss.harshesh.assignment.`interface`.WeatherInterface
import com.synchronoss.harshesh.assignment.presenter.WeatherPresenter
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity(), WeatherInterface.lastView{
    companion object {
        private const val TAG = "WeatherActivity"
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }
    private var mTextViewCountry: TextView? = null
    private var mTextViewCityName: TextView? = null
    private var mTextViewTemperature: TextView? = null
    private var mTextViewWeather: TextView? = null
    private var mTextViewTempMin: TextView? = null
    private var mTextViewTempMax: TextView? = null
    private var mTextViewSunrise: TextView? = null
    private var mTextViewSunset: TextView? = null
    private var mTextViewWind: TextView? = null
    private var mTextViewPressure: TextView? = null
    private var mTextViewHumidity: TextView? = null

    private var mRelativeLayoutBody: RelativeLayout? = null
    private var mLinearLayoutNoData: LinearLayout? = null

    private var mWeatherPresenter: WeatherPresenter? = null
    lateinit var receiver: UpdateUIReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        Constant.mlastview = this
        receiver = UpdateUIReceiver()
        IntentFilter("com.example.assignment").also {
            registerReceiver(receiver, it)
        }
    }

    override fun onResume() {
        Constant.mlastview = this
        permissionCheck()
        super.onResume()
    }

    override fun onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver)
            Constant.cancelJob(this)
        }
        super.onDestroy()
    }

    fun permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
            return
        } else {
            Constant.getLocation(this)
            if(!Constant.cityNameLocality.equals("")) {
                Constant.scheduleJob(this)
            }
        }
    }

    fun initialize() {
        mWeatherPresenter = WeatherPresenter()
        mTextViewCountry = findViewById(R.id.txt_country)
        mTextViewCityName = findViewById(R.id.txt_name)
        mTextViewTemperature = findViewById(R.id.txt_temperature)
        mTextViewWeather = findViewById(R.id.txt_weather)
        mTextViewTempMin = findViewById(R.id.txt_temp_min)
        mTextViewTempMax = findViewById(R.id.txt_temp_max)
        mTextViewSunrise = findViewById(R.id.txt_sunrise)
        mTextViewSunset = findViewById(R.id.txt_sunset)
        mTextViewWind = findViewById(R.id.txt_wind)
        mTextViewPressure = findViewById(R.id.txt_pressure)
        mTextViewHumidity = findViewById(R.id.txt_humidity)
        mRelativeLayoutBody = findViewById(R.id.ll_body)
        mLinearLayoutNoData = findViewById(R.id.ll_no_data)
    }

    override fun updateViewData() {
        if (Constant.weatherOBJ != null) {
            mRelativeLayoutBody!!.visibility = View.VISIBLE
            mLinearLayoutNoData!!.visibility = View.GONE

            var temperature = Constant.weatherOBJ?.mains!!.temp!!.toInt()
            mTextViewCountry!!.setText(Constant.weatherOBJ?.sys!!.country)
            mTextViewCityName!!.setText(Constant.weatherOBJ?.name)
            mTextViewTemperature!!.setText(temperature.toString())
            mTextViewWeather!!.setText(Constant.weatherOBJ?.weather!!.get(0)!!.main)
            mTextViewTempMin!!.setText(Constant.weatherOBJ?.mains!!.temp_min.toString()+"°C")
            mTextViewTempMax!!.setText(Constant.weatherOBJ?.mains!!.temp_max.toString()+"°C")
            mTextViewSunrise!!.setText(SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date((Constant.weatherOBJ?.sys!!.sunrise!!*1000).toLong())))
            mTextViewSunset!!.setText(SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date((Constant.weatherOBJ?.sys!!.sunset!!*1000).toLong())))
            mTextViewWind!!.setText(Constant.weatherOBJ?.winds!!.speed.toString())
            mTextViewPressure!!.setText(Constant.weatherOBJ?.mains!!.pressure.toString())
            mTextViewHumidity!!.setText(Constant.weatherOBJ?.mains!!.humidity.toString())
        }
    }

    override fun failedData(resultCode: Int, errorMessage: String) {
        mRelativeLayoutBody!!.visibility = View.GONE
        mLinearLayoutNoData!!.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> {
                    Constant.getLocation(this)
                    if(!Constant.cityNameLocality.equals("")) {
                        Constant.scheduleJob(this)
                    }
                }
                PackageManager.PERMISSION_DENIED -> ""
            }
        }
    }

    class UpdateUIReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val apiResult = intent?.getIntExtra(Constant.resultState,-1)
            Log.i(TAG, "UpdateUIReceiver result : " + apiResult)
            if (apiResult == 1) {
                Constant.mlastview!!.updateViewData()
            } else if (apiResult == 2) {
                var resultCode = intent?.getStringExtra(Constant.resultCode)
                var errorMessage = intent?.getStringExtra(Constant.resultCode)
                Constant.mlastview!!.failedData(resultCode!!.toInt(), errorMessage.toString())
            }
        }
    }
}

