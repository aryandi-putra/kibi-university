package com.aryandi.university.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class University(
    @SerializedName("web_pages")
    var webPages: List<String> = arrayListOf(),
    @SerializedName("name")
    var name: String
)