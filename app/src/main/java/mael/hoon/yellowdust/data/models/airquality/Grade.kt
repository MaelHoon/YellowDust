package mael.hoon.yellowdust.data.models.airquality

import androidx.annotation.ColorRes
import com.google.gson.annotations.SerializedName
import mael.hoon.yellowdust.R

//enum클래스를 생성하여 각각의 값들을 SerializedName을 이용하여 맵핑한다.
enum class Grade(
    val label: String,
    val emoji: String,
    //@colorRes어노테이션을 통해 실제로 colorRes가 아닌지 맞는지 판단해서 경고를 보여준다.
    @ColorRes val colorResId: Int
) {
    //Grade값이 1이 들어오면 GOOD으로 매핑된다.
    @SerializedName("1")//
    GOOD("좋음", "😆", R.color.blue),

    @SerializedName("2")
    NORMAL("보통", "😊", R.color.green),

    @SerializedName("3")
    BAD("나쁨", "😣", R.color.yellow),

    @SerializedName("4")
    AWFUL("매우나쁨", "😰", R.color.red),

    UNKNOWN("미측정", "🤔", R.color.gray);

    override fun toString(): String {
        return "$label $emoji"
    }
}