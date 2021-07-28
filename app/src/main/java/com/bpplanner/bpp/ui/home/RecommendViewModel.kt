package com.doubleslas.fifith.alcohol.ui.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.dto.RecommendList
import com.doubleslas.fifith.alcohol.sort.enum.RecommendSortType
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.MediatorApiCallback
import com.doubleslas.fifith.alcohol.utils.PageLoader

class RecommendViewModel(private val category: String) : ViewModel() {
    private val repository =
        RecommendRepository()
    private val pageLoader =
        PageLoader<AlcoholSimpleData>()
    val listLiveData: ApiLiveData<List<AlcoholSimpleData>> = pageLoader.liveData

    var sort =
        globalSortType
        private set

    fun initList(): Boolean {
        if (pageLoader.isInit() || sort != globalSortType) {
            loadList()
            return true
        }
        return false
    }

    fun loadList() {
        if (sort != globalSortType) {
            setSort(globalSortType)
            return
        }
        if (!pageLoader.canLoadList()) return

        val liveData = repository.getList(category, pageLoader.page++, sort)
        pageLoader.addObserve(liveData)
    }

    fun setSort(sortType: RecommendSortType) {
        if (sort == sortType) return
        sort = sortType
        globalSortType = sortType
        pageLoader.reset()
        loadList()
    }

    fun isFinishLoading(): Boolean{
        return pageLoader.isFinish()
    }

    class Factory(private val param: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(RecommendViewModel::class.java)) {
                RecommendViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }

    companion object {
        private var globalSortType: RecommendSortType = RecommendSortType.Recommend
    }
}