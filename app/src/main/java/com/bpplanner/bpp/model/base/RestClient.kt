package com.bpplanner.bpp.model.base

import com.bpplanner.bpp.BuildConfig
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.model.AuthRetrofit
import com.bpplanner.bpp.model.ConceptRetrofit
import com.bpplanner.bpp.model.ShopRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    fun getAuthService(): AuthRetrofit = retrofit.create(AuthRetrofit::class.java)
    fun getShopService(): ShopRetrofit = retrofit.create(ShopRetrofit::class.java)
    fun getConceptService(): ConceptRetrofit = retrofit.create(ConceptRetrofit::class.java)

    private val retrofit =
        Retrofit.Builder().run {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            val headerInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val token = MyApp.getPrefs().token
                    val request = chain.request().newBuilder()
                    if (token != null) {
                        request.addHeader(
                            "Authorization",
                            "Bearer ${token.access}"
                        )
                    }
                    return chain.proceed(request.build())
                }
            }

            val client = OkHttpClient.Builder().run {
                addInterceptor(loggingInterceptor)
                addInterceptor(headerInterceptor)
                build()
            }


            baseUrl("http://3.35.146.251:8000/") // 도메인 주소
            client(client)

            addConverterFactory(NullOnEmptyConverterFactory())
            addCallAdapterFactory(LiveDataCallAdapter.Factory())
            addConverterFactory(GsonConverterFactory.create()) // GSON을 사요아기 위해 ConverterFactory에 GSON 지정
            build()
        }
}

