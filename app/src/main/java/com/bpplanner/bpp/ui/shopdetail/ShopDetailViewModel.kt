package com.bpplanner.bpp.ui.shopdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.dto.ConceptData
import com.bpplanner.bpp.dto.ConceptList
import com.bpplanner.bpp.dto.ShopDetailData
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.model.base.MediatorApiLiveData
import com.bpplanner.bpp.utils.pagination.IPageLoaderViewModel
import com.bpplanner.bpp.utils.pagination.PageLoader

class ShopDetailViewModel(val id: Int) : ViewModel(), IPageLoaderViewModel {
    private val repository = ShopDetailRepository()
    private val pageLoader = PageLoader<ConceptData>()

    private val _dataLiveData = MediatorApiLiveData<ShopDetailData>()
    val dataLiveData: ApiLiveData<ShopDetailData> = _dataLiveData

    val conceptListLiveData: ApiLiveData<List<ConceptData>> = pageLoader.liveData


    fun getDetailData(): ApiLiveData<ShopDetailData> {
        _dataLiveData.addSource(repository.getDetailData(id))
        return dataLiveData
    }

    fun setLike(value: Boolean){
        repository.setLike(id, value)
    }

    override fun loadList() {
        if (!pageLoader.canLoadList()) return

        val liveData = repository.getConceptList(id, pageLoader.page++)
        pageLoader.addObserve(liveData)
    }

    override fun isFinishList(): Boolean {
        return pageLoader.isFinish
    }


    fun getBannerList() : Array<String> {
        val data = dataLiveData.value

        if (data is ApiStatus.Success){
            return data.data.profileImgList.toTypedArray()
        }

        return arrayOf()
    }

    fun inquireShop() : ApiLiveData<Void> {
        return repository.inquireShop(id)
    }


    class Factory(private val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(ShopDetailViewModel::class.java)) {
                ShopDetailViewModel(id) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}