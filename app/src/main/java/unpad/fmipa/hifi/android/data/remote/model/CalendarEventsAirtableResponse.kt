package unpad.fmipa.hifi.android.data.remote.model


import com.google.gson.annotations.SerializedName

data class CalendarEventsAirtableResponse(
    @SerializedName("offset")
    val offset: String?,
    @SerializedName("records")
    val records: List<Record>?
)

data class Fields(
    @SerializedName("Cover")
    val cover: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Tags")
    val tags: List<String>?,
    @SerializedName("Time")
    val time: String?,
    @SerializedName("Link")
    val link: String?
)

data class Record(
    @SerializedName("createdTime")
    val createdTime: String,
    @SerializedName("fields")
    val fields: Fields,
    @SerializedName("id")
    val id: String
)