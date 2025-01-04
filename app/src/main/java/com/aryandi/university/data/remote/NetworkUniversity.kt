package com.aryandi.university.data.remote

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUniversity(
    @SerializedName("name")
    var name: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("web_pages")
    var webPages: List<String> = arrayListOf(),
)