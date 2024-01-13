package mael.hoon.yellowdust.data

import mael.hoon.yellowdust.BuildConfig
import mael.hoon.yellowdust.data.models.airquality.MeasuredValue
import mael.hoon.yellowdust.data.models.monitoringstation.MonitoringStation
import mael.hoon.yellowdust.data.services.AirKoreaApiService
import mael.hoon.yellowdust.data.services.KakaoLocalApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//Retrofit 처리는 싱글톤으로 구현할 것 이기 때문에 object로 접근한다.
object Repository {

    suspend fun getNearbyMonitoringStation(latitude : Double, longtitude : Double) : MonitoringStation? {
        val tmCoordinates = kakaoLocalApiService
            .getTmCoordinate(longtitude,latitude)
            .body()
            ?.documents
            // 첫번째 값을 가져오고 그 값이 없으면 Null을 반환한다.(document가 배열로 처리 되기 때문에 null처리를 따로 해준다.)
            ?.firstOrNull()

        val tmX = tmCoordinates?.x
        val tmY = tmCoordinates?.y

        return airKoreaApiService
            .getNearbyMonitoringStation(tmX!!,tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            ?.minByOrNull { it!!.tm ?: Double.MAX_VALUE } //선택한 요소들중 가장 작은 값을 전달한다.
    }

    suspend fun getLatestAirQulityData(stationName : String) : MeasuredValue? =
        airKoreaApiService
            .getRealtimeAirQuilties(stationName)
            .body()
            ?.response
            ?.body
            ?.measuredValues
            ?.firstOrNull() //가장 최근에 측정된 값을 반환 한다.

    //정의한 KakaoApiService를 사용하기 위해서 retrofit 서비스를 생성한다.
    private val kakaoLocalApiService : KakaoLocalApiService by lazy{
        Retrofit.Builder()
            .baseUrl(Url.KAKAO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())//Logging을 추가하기 위해 client를 추가한다.
            .build()
            .create()
    }

    private val airKoreaApiService : AirKoreaApiService by lazy{
        Retrofit.Builder()
            .baseUrl(Url.AIR_KOREA_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private fun buildHttpClient() : OkHttpClient =
        OkHttpClient.Builder() //Logging처리를 위한 OkHttpClient
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    //Logging Level수준에 따라서 따로 처리를 해줘야 한다.
                    //Level에 따라서 보이는 정보의 수준을 정해준다.
                    level = if(BuildConfig.DEBUG){ // Debug일때만 로그를 오픈한다.
                        HttpLoggingInterceptor.Level.BODY
                    }else{
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}