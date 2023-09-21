package com.example.dogsproject.additional

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.replaceSpaceWithDash(): String {
    return this.replace(" ", "-")
}

fun String.splitToGetBreed(): String {
    return this.split("/")[4]
}

fun String.replaceDashWithSpace(): String {
    return this.replace("-", " ")
}

fun String.splitToGetBreedName(): String {
    return this.split(" ")[0]
}

fun String?.intoListOfString(): List<String> {
    return if(this == "") listOf<String>()
    else Gson().fromJson<List<String>?>(
        this, object : TypeToken<List<String>>() {}.type
    )
}