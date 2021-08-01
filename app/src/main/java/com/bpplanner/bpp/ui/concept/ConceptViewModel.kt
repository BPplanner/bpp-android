package com.bpplanner.bpp.ui.concept

import androidx.lifecycle.ViewModel
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.dto.ConceptData
import com.bpplanner.bpp.dto.ConceptFilter
import com.bpplanner.bpp.dto.IdValuePair
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.utils.pagination.IPageLoaderViewModel
import com.bpplanner.bpp.utils.pagination.PageLoader

class ConceptViewModel() : ViewModel(), IPageLoaderViewModel {
    private val repository = ConceptRepository()
    private val pageLoader = PageLoader<ConceptData>()
    private var likeFilter = false

    val conceptFilter by lazy {
        MyApp.getRemoteConfig().conceptFilter
    }

    val listLiveData: ApiLiveData<List<ConceptData>> = pageLoader.liveData

    override fun loadList() {
        if (!pageLoader.canLoadList()) return

        val liveData = repository.getConceptList(pageLoader.page++, likeFilter, conceptFilter)
        pageLoader.addObserve(liveData)
    }

    override fun isFinishList(): Boolean {
        return pageLoader.isFinish
    }


    fun setLikeFilter(value: Boolean){
        likeFilter = value
        reset()
        loadList()
    }

    fun setLikeConcept(item: ConceptData): ApiLiveData<Any> {
        item.like = !item.like
        return repository.setLikeConcept(item.id, item.like)
    }

    fun reset() {
        pageLoader.reset()
        loadList()
    }

}