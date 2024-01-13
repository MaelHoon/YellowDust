package mael.hoon.yellowdust.data.models.monitoringstation


import com.google.gson.annotations.SerializedName

data class Body(
    //Retrofit을 통해 API Gson데이터를 받아올때 SerializedName에 명시된 이름으로 파싱해서 받아온다.
    @SerializedName("items")
    val monitoringStations: List<MonitoringStation?>?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?
)