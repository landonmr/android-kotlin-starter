package com.landon.starterproject.models

import com.squareup.moshi.Json

data class CharactersResponse (
    val code: Int,
    val data: CharacterData
)

