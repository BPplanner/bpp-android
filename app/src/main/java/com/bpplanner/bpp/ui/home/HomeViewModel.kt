package com.bpplanner.bpp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.dto.IdValuePairCheckable
import com.bpplanner.bpp.dto.ShopData
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.utils.pagination.IPageLoaderViewModel
import com.bpplanner.bpp.utils.pagination.PageLoader

class HomeViewModel(private val index: Int) : ViewModel(), IPageLoaderViewModel {
    private val repository = HomeRepository()
    private val pageLoader = PageLoader<ShopData>()

    private val beautyFilter: Array<IdValuePairCheckable> by lazy {
        MyApp.getRemoteConfig().beautyFilterList
    }
    private val studioFilter: Array<IdValuePairCheckable> by lazy {
        MyApp.getRemoteConfig().studioFilterList
    }
    private var likeFilter = false


    val listLiveData: ApiLiveData<List<ShopData>> = pageLoader.liveData

    override fun loadList() {
        if (!pageLoader.canLoadList()) return

        val filterList =
            when (index) {
                1 -> beautyFilter
                else -> studioFilter
            }.filter { it.checked }
                .map { it.id }

        val liveData =
            when (index) {
                1 -> repository.getBeautyList(pageLoader.page++, likeFilter, filterList)
                else -> repository.getShopList(pageLoader.page++, likeFilter, filterList)
            }

        pageLoader.addObserve(liveData)
    }

    override fun isFinishList(): Boolean {
        return pageLoader.isFinish
    }

    fun getFilterList(): Array<IdValuePairCheckable> {
        return when (index) {
            1 -> beautyFilter
            else -> studioFilter
        }
    }

    fun setLikeFilter(value: Boolean) {
        likeFilter = value
        reset()
        loadList()
    }

    fun setLike(data: ShopData, value: Boolean) {
        data.like = value
        repository.setLike(data.id, value)
    }

    fun reset() {
        pageLoader.reset()
        loadList()
    }

    class Factory(private val param: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                HomeViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }

}