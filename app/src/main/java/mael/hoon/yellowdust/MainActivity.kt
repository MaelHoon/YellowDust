package mael.hoon.yellowdust

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import mael.hoon.yellowdust.data.Repository
import mael.hoon.yellowdust.data.models.airquality.Grade
import mael.hoon.yellowdust.data.models.airquality.MeasuredValue
import mael.hoon.yellowdust.data.models.monitoringstation.MonitoringStation
import mael.hoon.yellowdust.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //메인 엑티비티의 코루틴들을 관리한다.
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        requestLocationPermissions()
        bindViews()
        initVariables()
    }

    override fun onDestroy() {
        super.onDestroy()

        cancellationTokenSource?.cancel() // null이아닐경우 cancel시킨다.
        scope.cancel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationPermissionGranted =
            requestCode == REQUEST_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        val backgroundLocationPermissionGranted =
            requestCode == REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//R은 11버전
            if (!backgroundLocationPermissionGranted) {
                requestBackgroundLocationPermissions()
            } else {
                fetchAirQualityData()
            }
        } else {
            if (!locationPermissionGranted) {
                finish()
            } else {
                fetchAirQualityData()
            }
        }
    }

    private fun bindViews() {
        viewBinding.refresh.setOnRefreshListener {
            fetchAirQualityData()
        }
    }

    private fun initVariables() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestBackgroundLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
            REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS
        )
    }

    @SuppressLint("MissingPermission")
    private fun fetchAirQualityData() {
        // fetchData
        cancellationTokenSource = CancellationTokenSource()

        fusedLocationProviderClient.getCurrentLocation( //Task를 반환하기 때문에 addOnSuccessListener를 선언한다.
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource!!.token
        ).addOnSuccessListener { location ->
            scope.launch {
                viewBinding.errorDescriptionTextView.visibility = View.GONE
                try {
                    val monitoringStation =
                        Repository.getNearbyMonitoringStation(location.latitude, location.longitude)

                    val measuredValue =
                        Repository.getLatestAirQulityData(monitoringStation!!.stationName!!)

                    displayAirQualityData(monitoringStation, measuredValue!!)
                } catch (e: Exception) {
                    viewBinding.errorDescriptionTextView.visibility = View.VISIBLE
                    viewBinding.contentsLayout.alpha = 0F
                } finally {
                    with(viewBinding) {
                        progressBar.visibility = View.GONE
                        refresh.isRefreshing = false
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun displayAirQualityData(monitoringStation: MonitoringStation, measuredValue: MeasuredValue) {
        with(viewBinding) {
            contentsLayout.animate()
                .alpha(1F)
                .start()
            measuringStationNameTextView.text = monitoringStation.stationName
            measuringStationAddressTextView.text = "측정소 위치 : ${monitoringStation.addr}"

            (measuredValue.khaiGrade ?: Grade.UNKNOWN).let { grade ->
                root.setBackgroundResource(grade.colorResId)
                totalGradeLabelTextView.text = grade.label
                totalGradeEmojiTextView.text = grade.emoji
            }

            with(measuredValue) {
                fineDustInformationTextView.text =
                    "미세먼지 : $pm10Value ㎍/㎥ ${(pm10grade ?: Grade.UNKNOWN).emoji}"
                ultraFineDustInformationTextView.text =
                    "초미세먼지 : $pm25Value ㎍/㎥ ${(pm25grade ?: Grade.UNKNOWN).emoji}"

                with(viewBinding.so2Item) {
                    labelTextView.text = "아황산가스"
                    gradeTextView.text = (so2grade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$so2Value ppm"
                }
                with(viewBinding.co2Item) {
                    labelTextView.text = "일산화탄소"
                    gradeTextView.text = (coGrade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$coValue ppm"
                }
                with(viewBinding.o3Item) {
                    labelTextView.text = "오존"
                    gradeTextView.text = (o3grade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$o3Value ppm"
                }
                with(viewBinding.no2Item) {
                    labelTextView.text = "이산화질소"
                    gradeTextView.text = (no2grade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$no2Value ppm"
                }
            }
        }
    }

    companion object {
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 100
        private const val REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS = 101
    }
}