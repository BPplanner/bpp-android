package com.bpplanner.bpp.ui.launch

import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.dto.TokenData
import com.bpplanner.bpp.dto.TokenRequest
import com.bpplanner.bpp.model.AuthRetrofit
import com.bpplanner.bpp.model.base.*

class LaunchRepository {
    val retrofit: AuthRetrofit = RestClient.getAuthService()
    fun login(kakaoAccessToken: String): ApiLiveData<TokenData> {
        val mediator = MediatorApiLiveData<TokenData>()

        mediator.addSource(
            retrofit.login(TokenRequest(kakaoAccessToken, null)),
            object : MediatorApiSuccessCallback<TokenData> {
                override fun onSuccess(code: Int, data: TokenData) {
                    MyApp.getPrefs().token = data
                    mediator.value = ApiStatus.Success(code, data)
                }
            }
        )

        return mediator
    }

}