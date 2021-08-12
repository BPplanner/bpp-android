package com.bpplanner.bpp.model

import com.bpplanner.bpp.dto.TokenData
import com.bpplanner.bpp.dto.LoginRequest
import com.bpplanner.bpp.dto.TokenRequest
import com.bpplanner.bpp.model.base.ApiLiveData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AuthRetrofit {

    @POST("/login/new-token/")
    fun login(@Body token: LoginRequest): ApiLiveData<TokenData>

    @POST("/login/token/refresh/")
    fun newToken(@Body token: TokenRequest): Call<TokenData>

    @DELETE("login/withdrawal/")
    fun withdraw(): ApiLiveData<Any>
}

