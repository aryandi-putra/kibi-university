package com.aryandi.university.domain.mapper

import com.aryandi.university.data.local.DBUniversity
import com.aryandi.university.data.remote.ApiUniversity
import com.aryandi.university.domain.model.University

fun interface Mapper<in From, out To> {
    fun map(from: From): To
}

object DBToModelMapper : Mapper<DBUniversity, University> {
    override fun map(from: DBUniversity): University = University(
        name = from.name,
        country = from.country,
        webPage = from.webPage
    )
}

object ApiToDBMapper : Mapper<ApiUniversity, DBUniversity> {
    override fun map(from: ApiUniversity): DBUniversity = DBUniversity(
        name = from.name,
        country = from.country,
        webPage = from.webPages[0]
    )
}

object ApiToModelMapper : Mapper<ApiUniversity, University> {
    override fun map(from: ApiUniversity): University = University(
        name = from.name,
        country = from.country,
        webPage = from.webPages[0]
    )
}