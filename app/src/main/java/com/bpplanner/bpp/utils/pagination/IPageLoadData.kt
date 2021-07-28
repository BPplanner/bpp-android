package com.bpplanner.bpp.utils.pagination

interface IPageLoadData<T> {
    fun getList(): List<T>
}