package com.bpplanner.bpp.utils.pagination

interface IPageLoaderViewModel {
    fun loadList()
    fun isFinishList(): Boolean
}