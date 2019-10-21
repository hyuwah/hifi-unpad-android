package unpad.fmipa.hifi.android.data.remote.model


import com.google.gson.annotations.SerializedName

data class CalendarEventResponse(
    @SerializedName("category")
    val category: String = "",
    @SerializedName("date")
    val date: Int = 0,
    @SerializedName("event")
    val event: String = "",
    @SerializedName("month")
    val month: Int = 0,
    @SerializedName("year")
    val year: Int = 0
)