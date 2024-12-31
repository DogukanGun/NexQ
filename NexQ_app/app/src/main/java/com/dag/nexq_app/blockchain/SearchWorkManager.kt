package com.dag.nexq_app.blockchain

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltWorker
class SearchWorkManager @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val workerParams: WorkerParameters
): Worker(appContext, workerParams)  {

    override fun doWork(): Result {
        workerParams.inputData.getString("wallet_key")?.let {
            val scope = CoroutineScope(
                SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
                    println("Caught exception: $exception")
                }
            )

            // Start coroutine to handle the sensor logic
            scope.launch {
                // Use handler to execute sensor actions on the main thread
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val sensorManager = appContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                    val pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

                    // Create a listener to capture sensor data
                    val sensorEventListener = object : SensorEventListener {
                        override fun onSensorChanged(event: SensorEvent?) {
                            event?.let {
                                if (it.sensor.type == Sensor.TYPE_PRESSURE) {
                                    val pressure = it.values[0]

                                    println("Pressure: $pressure")
                                }
                            }
                        }

                        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                            // Handle accuracy changes if needed
                        }
                    }

                    // Register the sensor event listener
                    sensorManager.registerListener(
                        sensorEventListener,
                        pressureSensor,
                        SensorManager.SENSOR_DELAY_NORMAL
                    )

                    sensorManager.unregisterListener(sensorEventListener)
                }
            }

            return Result.success()
        }

        return Result.failure()
    }

    private fun fetchLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            val location = fusedLocationClient.lastLocation.result
                val latitude = location.latitude
                val longitude = location.longitude
                val altitude = location.altitude

                println("Location: Latitude: $latitude, Longitude: $longitude, Altitude: $altitude")

        } catch (e: SecurityException) {
            println("Location permission not granted: $e")
        }
    }

}