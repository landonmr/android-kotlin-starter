package com.landon.starterproject.networking

import com.landon.starterproject.models.CharactersResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface MarvelApi {
    @GET("/v1/public/characters")
    fun getCharacters() : Observable<CharactersResponse>
}