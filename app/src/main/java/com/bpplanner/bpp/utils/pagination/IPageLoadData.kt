package com.bpplanner.bpp.utils.pagination

interface IPageLoadData<T> {
    fun getTotalCount(): Int
    fun getList(): List<T>
}