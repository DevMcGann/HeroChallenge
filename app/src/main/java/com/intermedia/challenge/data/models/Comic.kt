package com.intermedia.challenge.data.models

import com.google.gson.annotations.SerializedName


data class Comic(
    val id: Int = 0,
    val title: String = "",
    val thumbnail: Thumbnail = Thumbnail(),
    val dates: List<Dates>,
    val publishDate: String,
    val events: Events? = null
)

data class Events (
    val available:Int = 0,
    val collectionURI: String = "",

    @SerializedName("events")
    val items : List<Event> = listOf(),
    val returned: Int = 0
)


data class Dates(
    val type : String = "",
    val date : String
)



