package com.landon.starterproject.networking

import com.landon.starterproject.extension.md5
import com.landon.starterproject.models.CharactersResponse
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


open class Network : MarvelApi {

    private lateinit var marvelApi: MarvelApi

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)


        val ts: String = System.currentTimeMillis().toString()
        val privateKey: String = "b066c3133518003e1daedcaa5d7b89d97705deba"
        val publicKey: String = "14e27eb2697c17facb953ec5f1454fc6"
        val hash: String = "$ts$privateKey$publicKey".md5()

        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", publicKey)
                .addQueryParameter("hash", hash)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(logging)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com:443/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        marvelApi = retrofit.create(MarvelApi::class.java)
    }

    override fun getCharacters(): Observable<CharactersResponse> {
        return marvelApi.getCharacters()
    }
}