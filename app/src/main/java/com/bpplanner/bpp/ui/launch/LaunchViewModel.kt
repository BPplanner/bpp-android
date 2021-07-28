package com.bpplanner.bpp.ui.launch

import androidx.lifecycle.ViewModel
import com.bpplanner.bpp.dto.TokenData
import com.bpplanner.bpp.model.base.ApiLiveData

class LaunchViewModel : ViewModel() {
    private val repository: LaunchRepository = LaunchRepository()

    fun loginKakao(kakaoAccessToken: String): ApiLiveData<TokenData> {
        return repository.login(kakaoAccessToken)
    }
}