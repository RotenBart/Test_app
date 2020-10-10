package com.example.testapp.api

import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*

object ApiHolder  {
    var API_BASE_URL: String = "https://gitlab.65apps.com/"
    private const val JSON_ACCEPT_TYPE = "application/json; charset=UTF-8"
    private var jsonApi: JsonApi

    init {
        jsonApi =
            createBaseRetrofitBuilder().addConverterFactory(GsonConverterFactory.create(createGson()))
                .client(createHttpClient().build())
                .build()
                .create(JsonApi::class.java)
    }

    private fun createBaseRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    private fun createHttpClient(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
    }

    private fun createGson(): Gson {
        val gson = GsonBuilder().setLenient()
        gson.registerTypeAdapter(Date::class.java,
            JsonDeserializer { json: JsonElement, typeOfT: Type?, context1: JsonDeserializationContext? ->
                Date(
                    json.asJsonPrimitive.asLong
                )
            } as JsonDeserializer<Date>)
        gson.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        return gson.create()
    }

    fun getJsonApi(): JsonApi {
        return jsonApi
    }
}