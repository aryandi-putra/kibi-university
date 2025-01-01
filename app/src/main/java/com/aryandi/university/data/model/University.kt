package com.aryandi.university.data.model

import kotlinx.serialization.Serializable

@Serializable
data class University(
    var webPages: ArrayList<String> = arrayListOf(),
    var name: String? = null
)