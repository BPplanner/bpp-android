package com.bpplanner.bpp.model

import com.bpplanner.bpp.dto.TokenData
import com.bpplanner.bpp.dto.TokenRequest
import com.bpplanner.bpp.model.base.ApiLiveData
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofit {

    @POST("/login/new-tokens/")
    fun login(@Body token: TokenRequest): ApiLiveData<TokenData>

    @POST("/login/token/refresh/")
    fun newToken(@Body token: TokenRequest): ApiLiveData<TokenData>

}
