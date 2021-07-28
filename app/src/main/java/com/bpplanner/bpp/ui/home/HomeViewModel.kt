package com.bpplanner.bpp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.dto.ShopList
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.utils.pagination.PageLoader

class HomeViewModel(private val index: Int) : ViewModel() {
    private val repository = HomeRepository()
    private val pageLoader = PageLoader<ShopList>()
    val listLiveData: ApiLiveData<List<ShopList>> = pageLoader.liveData

    fun initList(): Boolean {
        if (pageLoader.isInit()) {
            loadList()
            return true
        }
        return false
    }

    fun loadList() {
        if (!pageLoader.canLoadList()) return

        val liveData =
            when (index) {
                1 -> repository.getBeautyList(pageLoader.page++, arrayOf())
                else -> repository.getShopList(pageLoader.page++, arrayOf())
            }

//        pageLoader.addObserve(liveData)
    }

    fun isFinishLoading(): Boolean {
        return pageLoader.isFinish()
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