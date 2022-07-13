package com.daniel.finalassignment.ui

import java.io.Serializable

class CharactersData(
    val id: Int,
    val name: String,
    val nickname: String,
    val occupation: List<String>,
    val status: String,
    val actor: String,
    val img: String


): Serializable