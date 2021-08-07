package com.bpplanner.bpp.ui.concept

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.dto.ConceptData
import com.bpplanner.bpp.dto.ConceptFilter
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.utils.pagination.IPageLoaderViewModel
import com.bpplanner.bpp.utils.pagination.PageLoader

class ConceptViewModel() : ViewModel(), IPageLoaderViewModel {
    private val repository = ConceptRepository()
    private val pageLoader = PageLoader<ConceptData>()
    private var likeFilter = false

    private val _conceptFilter = MutableLiveData<ConceptFilter>()
    val conceptFilter: LiveData<ConceptFilter> = _conceptFilter

    val listLiveData: ApiLiveData<List<ConceptData>> = pageLoader.liveData

    init {
        _conceptFilter.value = MyApp.getRemoteConfig().conceptFilter
    }


    override fun loadList() {
        if (!pageLoader.canLoadList()) return

        val liveData =
            repository.getConceptList(pageLoader.page++, likeFilter, conceptFilter.value!!)
        pageLoader.addObserve(liveData)
    }

    override fun isFinishList(): Boolean {
        return pageLoader.isFinish
    }


    fun setLikeFilter(value: Boolean) {
        likeFilter = value
        reset()
        loadList()
    }

    fun setLikeConcept(item: ConceptData): ApiLiveData<Any> {
        item.like = !item.like
        return repository.setLikeConcept(item.id, item.like)
    }

    fun setConceptFilter(filter: ConceptFilter) {
        _conceptFilter.value = filter
        reset()
    }

    fun getData(index: Int): ConceptData {
        return pageLoader.getList()[index]
    }

    private fun reset() {
        pageLoader.reset()
        loadList()
    }

}