package com.vk59.diary_simbirsoft.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class TaskJson (
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("date_start")
    @Expose
    var dateStart: String,

    @SerializedName("date_finish")
    @Expose
    var dateFinish: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("description")
    @Expose
    var description: String
    ) {
}