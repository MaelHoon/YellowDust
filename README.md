사용기술
1. 위치정보 받기
   - fusedLocationProvider
3. API호출
  - Retrofit2
4. API비동기 처리
  - Coroutine
5. App Widgets

API호출 구조
![미세먼지앱 api호출구조](https://github.com/MaelHoon/YellowDust/assets/149458609/310db78d-0ed3-44ad-bc43-32ebc560ca04)
1. 안드로이드 앱 에서 현재 사용자의 경도 위도를 가져온다.
2. 해당 좌표를 카카오 디벨로퍼의 지도/로컬을 이용해 TM좌표로 변환한다.
3. 공공데이터 포털로 TM좌표를 전달해서 근처 측정소명(TM좌표에 따른 근접 데이터)을 가져온다
4. 해당 측정소명 값을 바탕으로 공공데이터 포털에서 실시간 미세먼지 값을 받아온다.

airQaulity Model 정보
![image](https://github.com/MaelHoon/YellowDust/assets/149458609/b3ff8c2a-238f-4c2a-b55d-c4693321dd38)
