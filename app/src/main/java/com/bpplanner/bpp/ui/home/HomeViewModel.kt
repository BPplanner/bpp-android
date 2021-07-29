package com.bpplanner.bpp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.dto.IdValuePair
import com.bpplanner.bpp.dto.ShopData
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.utils.RemoteConfigUtil
import com.bpplanner.bpp.utils.pagination.IPageLoaderViewModel
import com.bpplanner.bpp.utils.pagination.PageLoader

class HomeViewModel(private val index: Int) : ViewModel(), IPageLoaderViewModel {
    private val repository = HomeRepository()
    private val pageLoader = PageLoader<ShopData>()
    private var filterList: List<String>? = null

    val listLiveData: ApiLiveData<List<ShopData>> = pageLoader.liveData

    override fun loadList() {
        if (!pageLoader.canLoadList()) return

        val liveData =
            when (index) {
                1 -> repository.getBeautyList(pageLoader.page++, filterList)
                else -> repository.getShopList(pageLoader.page++, filterList)
            }

        pageLoader.addObserve(liveData)
    }

    override fun isFinishList(): Boolean {
        return pageLoader.isFinish
    }

    fun getFilterList(): Array<IdValuePair> {
        return when (index) {
            1 -> MyApp.getRemoteConfig().beautyFilterList
            else -> MyApp.getRemoteConfig().studioFilterList
        }
    }

    fun setFilter(arr: List<String>){
        reset()
        filterList = arr
        loadList()
    }

    private fun reset(){
        pageLoader.reset()
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