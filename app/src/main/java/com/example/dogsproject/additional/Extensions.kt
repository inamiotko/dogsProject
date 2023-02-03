package com.example.dogsproject.additional

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