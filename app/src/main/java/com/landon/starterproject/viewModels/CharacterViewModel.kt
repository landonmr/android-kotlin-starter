package com.landon.starterproject.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.landon.starterproject.data.CharacterRepository
import com.landon.starterproject.models.Character
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

enum class ViewState {
    Loading,
    Loaded,
    Error
}

class CharacterViewModel : ViewModel(), KoinComponent {
    private val characterRepository : CharacterRepository by inject()

    val state: MutableLiveData<ViewState> = MutableLiveData()
    init {
        state.value = ViewState.Loading
    }

    var characters: List<Character> = ArrayList(arrayListOf())

    @SuppressLint("CheckResult")
    fun loadCharacters() {
         characterRepository.getCharacters()
             .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { c -> handleSuccess(c) },
                { e -> handleError(e) }
            )
    }

    private  fun handleSuccess(c: List<Character>) {
        characters = c
        state.value = ViewState.Loaded
    }

    private fun handleError(error: Throwable) {
        state.value = ViewState.Error
    }
}