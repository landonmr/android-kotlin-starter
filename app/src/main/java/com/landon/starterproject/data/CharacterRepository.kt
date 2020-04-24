package com.landon.starterproject.data

import com.landon.starterproject.models.Character
import com.landon.starterproject.networking.Network
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

open class CharacterRepository : KoinComponent {
    private val networkClient : Network by inject()

    open fun getCharacters(): Observable<List<Character>> {

        return networkClient.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { result -> result.data.results }
    }
}