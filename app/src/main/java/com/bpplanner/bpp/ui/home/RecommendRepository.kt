package com.doubleslas.fifith.alcohol.ui.recommend

import com.doubleslas.fifith.alcohol.sort.enum.RecommendSortType
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient
import com.doubleslas.fifith.alcohol.dto.RecommendList

class RecommendRepository {
    private val service by lazy { RestClient.getRecommendService() }

    fun getList(category: String, page: Int, sort: RecommendSortType): ApiLiveData<RecommendList> {
        return service.getRecommendList(category, page, sort.sort, sort.sortOption)
    }
}
