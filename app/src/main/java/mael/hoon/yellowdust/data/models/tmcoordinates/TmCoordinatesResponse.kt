package mael.hoon.yellowdust.data.models.tmcoordinates

import com.google.gson.annotations.SerializedName
/*gson모델을 정의 한다. API는 필요한 응답을 보고 처리하는게 좋은데,
이렇게 하면 시간이 오래 걸리기 때문에
안드로이드 스튜디오 설정 -> plugin -> json to kotlin plugin을 다운 받는다.
이는 json응답 형식을 받아서 그걸 토대로 자동으로 모델을 만들어주는 플러그인이다.
OpenAPI를 빠르게 테스트 해보고 싶을때 주로 사용한다.
gson모델을 추가할 패키지에 New -> Kotiln data class file to json을 누르고
OpenAPI의 응답 양식(Response 샘플데이터)을 추가한다. advance를 눌러 nullable설정을 해준다.*/

//retrofit Model설정
data class TmCoordinatesResponse(
    @SerializedName("documents")
    val documents: List<Document?>?,
    @SerializedName("meta")
    val meta: Meta?
)