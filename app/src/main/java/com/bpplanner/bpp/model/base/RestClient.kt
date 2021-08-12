package com.bpplanner.bpp.model.base

import com.bpplanner.bpp.BuildConfig
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.dto.TokenRequest
import com.bpplanner.bpp.model.AuthRetrofit
import com.bpplanner.bpp.model.ConceptRetrofit
import com.bpplanner.bpp.model.ReservationRetrofit
import com.bpplanner.bpp.model.ShopRetrofit
import com.bpplanner.bpp.utils.JWT
import com.bpplanner.bpp.utils.LogUtil
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    fun getAuthService(): AuthRetrofit = retrofit.create(AuthRetrofit::class.java)
    fun getShopService(): ShopRetrofit = retrofit.create(ShopRetrofit::class.java)
    fun getConceptService(): ConceptRetrofit = retrofit.create(ConceptRetrofit::class.java)
    fun getReservationService(): ReservationRetrofit = retrofit.create(ReservationRetrofit::class.java)

    private var token = MyApp.getPrefs().token

    private val retrofit =
        Retrofit.Builder().run {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            val headerInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder()
                    if (token == null){
                        token = MyApp.getPrefs().token
                    }
                    if (token != null) {
                        request.addHeader(
                            "Authorization",
                            "Bearer ${token!!.access}"
                        )
                    }
                    return chain.proceed(request.build())
                }
            }

            val authenticator = object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.code == 401) {
                        val token = MyApp.getPrefs().token!!
                        MyApp.getPrefs().token = null
                        val userId = JWT.getUserId(token.access)
                        val authResult = getAuthService()
                            .newToken(TokenRequest(userId, token.refresh!!)).execute()

                        if (authResult.isSuccessful) {
                            val newToken = authResult.body()!!
                            if (newToken.refresh == null) newToken.refresh = token.refresh
                            MyApp.getPrefs().token = token
                            this@RestClient.token = token

                            return response.request
                        }
                    }
                    return null
                }
            }

            val client = OkHttpClient.Builder().run {
                authenticator(authenticator)
                addInterceptor(loggingInterceptor)
                addInterceptor(headerInterceptor)
                build()
            }


            baseUrl("http://3.35.146.251:8000/") // 도메인 주소
            client(client)

            addConverterFactory(NullOnEmptyConverterFactory())
            addConverterFactory(GsonConverterFactory.create()) // GSON을 사요아기 위해 ConverterFactory에 GSON 지정
            addCallAdapterFactory(LiveDataCallAdapter.Factory())
            build()
        }
}

