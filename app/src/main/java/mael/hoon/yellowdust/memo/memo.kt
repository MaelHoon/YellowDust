package mael.hoon.yellowdust.memo

object memo {
    /*
    <widget_simple_info>
    android:updatePeriodMillis="3600000"
    위젯의 업데이트 주기를 결정한다. 이 주기 마다 onUpdate라는 provide메소드가 호출되게 된다.
    화면이 꺼져있는 상태일때 업데이트 주기가 되면 핸드폰이 켜졌다가 업데이트를 하고 꺼지게된다.
    주기가 짧을수록 앱에서 사용하는 베터리 소모양이 증가한다.
    * 이 주기는 30분 미만으로 설정 할 수 없다. (최대 30분) -> 15분 10분 단위는 안됨
    만약 앱 스펙상 이보다 짧게 구현해야 한다면 updatePeriodMillis값을 0으로 주고
    alaramManager같은 것을 활용해서 구현 해야 한다.
     */
}