package com.synchronoss.harshesh.assignment.`interface`

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.location.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.synchronoss.harshesh.assignment.model.modelClass.Weather
import com.synchronoss.harshesh.assignment.view.WeatherJobService
import java.util.*

interface Constant {
    companion object {
        private const val TAG = "Constant"

        // API keys
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val CAPITAL = "weather"
        const val APIKEY = "56ca5dc37a33690e36f200cc17b105b4"

        // Location keys
        var latitute : Double = 0.0
        var longitute : Double = 0.0

        // Job scheduler keys
        const val JOB_ID = 123
        const val PERIODIC_TIME: Long = 119 * 60 * 1000
        const val SUCCESS_KEY = "SUCCESS"
        const val FAILED_KEY = "FAILED"

        // Passing Keys
        var successFailedState = -1
        var resultState = "result"
        var resultCode = "resultCode"
        var errorMessage = "errorMessage"
        var cityNameLocality = ""

        var mlastview: WeatherInterface.lastView? = null
        lateinit var weatherOBJ: Weather

        @SuppressLint("MissingPermission")
        fun getLocation(context:Context) {
            var locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?

            var locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    latitute = location!!.latitude
                    longitute = location!!.longitude
                    Log.i(TAG, "Latitute: $latitute ; Longitute: $longitute")
                    if(latitute != null && latitute != 0.0 && longitute != null && longitute != 0.0) {
                        var geocoder: Geocoder = Geocoder(context, Locale.getDefault())
                        var address: List<Address> =
                            geocoder.getFromLocation(Constant.latitute, Constant.longitute, 1)
                        var cityName = address[0].locality
                        cityNameLocality = cityName
                        scheduleJob(context)
                    } else {
                        Log.i(TAG, "location service disable or location not available")
                    }
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }

                override fun onProviderEnabled(provider: String) {
                }

                override fun onProviderDisabled(provider: String) {
                }
            }

            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0L,0f,locationListener)

        }

        private fun isJobRunning(jobId:Int,context:Context): Boolean {
            val jobScheduler: JobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            var isFlag: Boolean = false
            for(jobinfo: JobInfo in jobScheduler.allPendingJobs){
                if(jobinfo.id == jobId){
                    isFlag = true
                    break
                }
            }
            return isFlag
        }
        fun scheduleJob(context:Context) {
            if (!isJobRunning(JOB_ID, context)) {
                val componentName = ComponentName(context, WeatherJobService::class.java)
                val info = JobInfo.Builder(JOB_ID, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setRequiresDeviceIdle(false)
                    .setRequiresCharging(true)
                    .setPersisted(true)
                    .setPeriodic(PERIODIC_TIME)
                    .build()

                val jobScheduler: JobScheduler =
                    context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                val resultCode = jobScheduler.schedule(info)

                val isJobScheduledSuccess = resultCode == JobScheduler.RESULT_SUCCESS
                Log.d( TAG,"Job Scheduled ${if (isJobScheduledSuccess) Constant.SUCCESS_KEY else Constant.FAILED_KEY}")
            }
        }

        fun cancelJob(context:Context) {
            val jobScheduler: JobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(Constant.JOB_ID)
            Log.d(TAG, "Job CANCELED")
        }
    }
}