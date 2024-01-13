package mael.hoon.yellowdust.data.services

import mael.hoon.yellowdust.BuildConfig
import mael.hoon.yellowdust.data.models.tmcoordinates.TmCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

//API데이터 통신을 위한 Retrofit세팅
interface KakaoLocalApiService {

    // API요청시 인증 정보를 전달 REST API키를 전달 해줘야 한다. REST API키는 보통 gradle.properties에 정의하고,
    //이를 build.gradle의 defaultConfig에 buildConfigField로 추가한다.
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("v2/local/geo/transcoord.json?output_coord=TM") // API요청 쿼리문
    suspend fun getTmCoordinate(
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ) : Response<TmCoordinatesResponse>
}