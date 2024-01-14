● 메인 페이지

![미세먼지앱](https://github.com/MaelHoon/YellowDust/assets/149458609/3317a1fb-de39-4c78-a0a4-116656aaf5b3)

● 사용기술
1. 위치정보 받기
   - fusedLocationProvider
3. API호출
  - Retrofit2
4. API비동기 처리
  - Coroutine
5. App Widgets

● API모델 자동 생성 활용
gson모델을 정의 한다. API는 필요한 응답을 보고 처리하는게 좋은데,
이렇게 하면 시간이 오래 걸리기 때문에
안드로이드 스튜디오 설정 -> plugin -> json to kotlin plugin을 다운 받는다.
이는 json응답 형식을 받아서 그걸 토대로 자동으로 모델을 만들어주는 플러그인이다.
OpenAPI를 빠르게 테스트 해보고 싶을때 주로 사용한다.
gson모델을 추가할 패키지에 New -> Kotiln data class file to json을 누르고
OpenAPI의 응답 양식(Response 샘플데이터)을 추가한다. advance를 눌러 nullable설정을 해준다.

● API호출 구조
![미세먼지앱 api호출구조](https://github.com/MaelHoon/YellowDust/assets/149458609/310db78d-0ed3-44ad-bc43-32ebc560ca04)
1. 안드로이드 앱 에서 현재 사용자의 경도 위도를 가져온다.
2. 해당 좌표를 카카오 디벨로퍼의 지도/로컬을 이용해 TM좌표로 변환한다.
3. 공공데이터 포털로 TM좌표를 전달해서 근처 측정소명(TM좌표에 따른 근접 데이터)을 가져온다
4. 해당 측정소명 값을 바탕으로 공공데이터 포털에서 실시간 미세먼지 값을 받아온다.

● airQaulity Model 정보
![image](https://github.com/MaelHoon/YellowDust/assets/149458609/b3ff8c2a-238f-4c2a-b55d-c4693321dd38)

● airQulity grade 정보
![image](https://github.com/MaelHoon/YellowDust/assets/149458609/2fdc7e78-1eec-4eaa-870b-67e20c86e882)

● 기타 메모
1. themes.xml에서 <item name="android:windowTranslucentStatus">true</item> 속성을 주면
   기기의 상단 툴바색상이 앱의 background색상에 영향을 받게 설정 할 수 있다.
2. Manifest.xml에서 activity안에 android:screenOrientation="portrait" 속성을 주면 화면 회전을 막을 수 있다.
